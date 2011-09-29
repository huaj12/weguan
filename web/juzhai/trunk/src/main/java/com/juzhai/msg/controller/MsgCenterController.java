package com.juzhai.msg.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.account.bean.ConsumeAction;
import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.act.InitData;
import com.juzhai.app.service.IAppService;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.controller.view.ActMsgView;
import com.juzhai.msg.service.IActMsgService;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "msg")
public class MsgCenterController extends BaseController {

	@Autowired
	private IActMsgService actMsgService;
	@Autowired
	private IMsgMessageService sgMessageService;
	@Autowired
	private IProfileService profileService;
	@Value("${unread.actmsg.rows}")
	private int unReadActMsgRows = 20;
	@Value("${read.actmsg.rows}")
	private int readActMsgRows = 20;
	@Autowired
	IAccountService accountService;

	@RequestMapping(value = "/showUnRead", method = RequestMethod.GET)
	public String showUnRead(HttpServletRequest request, Model model,
			Integer page) throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		doPageUnRead(context.getUid(), page, model);
		return "msg/unRead";
	}


	private void doPageUnRead(long uid, int page, Model model) {
		long totalCount = actMsgService.countUnRead(uid);
		PagerManager pager = new PagerManager(page, unReadActMsgRows, Long
				.valueOf(totalCount).intValue());
		pager.setStringUrl("/msg/showUnRead?");
		List<ActMsg> actMsgList = actMsgService.pageUnRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("readCount", actMsgService.countRead(uid));
		model.addAttribute("pager", pager);
		model.addAttribute("point",accountService.queryPoint(uid));
		model.addAttribute("citys", com.juzhai.passport.InitData.CITY_MAP);
		
	}

	private List<ActMsgView> assembleActMsgView(long uid,
			List<ActMsg> actMsgList) {
		List<ActMsgView> actMsgViewList = new ArrayList<ActMsgView>(
				actMsgList.size());
		for (ActMsg actMsg : actMsgList) {
			ActMsgView actMsgView = new ActMsgView();
			actMsgView.setAct(InitData.ACT_MAP.get(actMsg.getActId()));
			actMsgView.setProfileCache(profileService
					.getProfileCacheByUid(actMsg.getUid()));
			actMsgView.setMsgType(actMsg.getType());
			actMsgView.setStuts(actMsg.isStuts());
			actMsgViewList.add(actMsgView);
		}
		return actMsgViewList;
	}

	@RequestMapping(value = "/showRead", method = RequestMethod.GET)
	public String showRead(HttpServletRequest request, Model model, Integer page)
			throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		doPageRead(context.getUid(), model, page);
		return "msg/read";
	}


	private void doPageRead(long uid, Model model, int page) {
		long totalCount = actMsgService.countRead(uid);
		PagerManager pager = new PagerManager(page, readActMsgRows, Long
				.valueOf(totalCount).intValue());
		pager.setStringUrl("/msg/showRead?");
		List<ActMsg> actMsgList = actMsgService.pageRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("unReadCount", actMsgService.countUnRead(uid));
		model.addAttribute("citys", com.juzhai.passport.InitData.CITY_MAP);
		model.addAttribute("point",accountService.queryPoint(uid));
	}

	@RequestMapping(value = "/unReadCnt", method = RequestMethod.GET)
	@ResponseBody
	public String unReadCnt(HttpServletRequest request, Model model) {
		Map<String, Long> result = new HashMap<String, Long>(1);
		try {
			UserContext context = checkLoginForApp(request);
			result.put("unReadCnt", actMsgService.countUnRead(context.getUid()));
		} catch (NeedLoginException e) {
			result.put("unReadCnt", 0L);
		}
		try {
			return JackSonSerializer.toString(result);
		} catch (JsonGenerationException e1) {
			return "{\"unReadCnt\":0}";
		}
	}

	/**
	 * 打开未读信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws NeedLoginException
	 */
	@RequestMapping(value = "/openMessage", method = RequestMethod.GET)
	@ResponseBody
	public String openMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer curPage,
			Integer curIndex) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		PrintWriter out = null;
		try {
			if (curPage != null && curIndex != null) {
				response.setContentType("text/plain");
				out = response.getWriter();
				// 查询积分
				int point=accountService.queryPoint(context.getUid());
				// 判断积分余额
				int openMsgPoint=com.juzhai.account.InitData.CONSUME_ACTION_RULE.get(ConsumeAction.OPEN_MESSAGE);
				if (point+openMsgPoint>0) {
					int index = (curPage-1) * unReadActMsgRows + curIndex;
					actMsgService.openMessage(context.getUid(), index);
					accountService.consumePoint(context.getUid(), ConsumeAction.OPEN_MESSAGE);
					out.print(1);
				} else {
					// 积分余额不足
					out.print(0);
				}
			} else {
				out.print(-1);
			}
		} catch (Exception e) {
			// 未知错误
			out.print(-1);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/reMoveMessage", method = RequestMethod.POST)
	public String reMoveMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer curPage,
			Integer curIndex,String type) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		try{
			if (curPage != null && curIndex != null) {
				int index = (curPage-1) * unReadActMsgRows + curIndex;
				if("unread".equals(type)){
					actMsgService.removeUnRead(context.getUid(), index);
				}else if("read".equals(type)){
					actMsgService.removeRead(context.getUid(), index);
				}
			}
		}catch (Exception e) {
				
		}
		 return null;
	}
	@RequestMapping(value = "/agreeMessage", method = RequestMethod.POST)
	public String agreeMessage(HttpServletRequest request,
			HttpServletResponse response, Model model,Long actId,Long receiverId, Integer curPage,
			Integer curIndex) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		PrintWriter out = null;
		try{
			response.setContentType("text/plain");
			out = response.getWriter();
			if(actId!=null&&receiverId!=null&&curPage != null && curIndex != null){
				int index = (curPage-1) * unReadActMsgRows + curIndex;
				//改变消息状态
				actMsgService.updateMsgStuts(context.getUid(), index);
				ActMsg msg=new ActMsg(actId,MsgType.INVITE);
				//发送拒宅邀请
				sgMessageService.sendActMsg(context.getUid(), receiverId, msg);
				out.println(1);
			}else{
				out.println(0);	
			}
		}catch (Exception e) {
			e.printStackTrace();
			out.println(0);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
}
