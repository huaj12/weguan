package com.juzhai.msg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.account.bean.ConsumeAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.act.InitData;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.controller.view.ActMsgView;
import com.juzhai.msg.service.IActMsgService;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "msg")
public class MsgCenterController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IActMsgService actMsgService;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private IProfileService profileService;
	@Value("${unread.actmsg.rows}")
	private int unReadActMsgRows = 1;
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
		ProfileCache profileCache = queryProfile(context.getUid(), model);
		if (profileCache != null && !profileCache.getSubEmail()) {
			showNotSubEmailTip(model);
		}
		doPageUnRead(context.getUid(), page, model);
		return "msg/app/unRead";
	}

	@RequestMapping(value = "/pageUnRead", method = RequestMethod.GET)
	public String pageUnRead(HttpServletRequest request, Model model,
			Integer page) throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		doPageUnRead(context.getUid(), page, model);
		return "msg/app/ajax/unReadContent";
	}

	private void doPageUnRead(long uid, int page, Model model) {
		long totalCount = actMsgService.countUnRead(uid);
		PagerManager pager = new PagerManager(page, unReadActMsgRows, Long
				.valueOf(totalCount).intValue(), "/msg/pageUnRead?",
				"unReadContent");
		List<ActMsg> actMsgList = actMsgService.pageUnRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("readCount", actMsgService.countRead(uid));
		model.addAttribute("pager", pager);
		model.addAttribute("point", accountService.queryPoint(uid));
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
			actMsgView.setDate(actMsg.getDate());
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
		ProfileCache profileCache = queryProfile(context.getUid(), model);
		if (profileCache != null && !profileCache.getSubEmail()) {
			showNotSubEmailTip(model);
		}
		doPageRead(context.getUid(), model, page);
		return "msg/app/read";
	}

	@RequestMapping(value = "/pageRead", method = RequestMethod.GET)
	public String pageRead(HttpServletRequest request, Model model, Integer page)
			throws NeedLoginException {
		if (null == page)
			page = 1;
		UserContext context = checkLoginForApp(request);
		doPageRead(context.getUid(), model, page);
		return "msg/app/ajax/readContent";
	}

	private void doPageRead(long uid, Model model, int page) {
		long totalCount = actMsgService.countRead(uid);
		PagerManager pager = new PagerManager(page, readActMsgRows, Long
				.valueOf(totalCount).intValue(), "/msg/pageRead?",
				"readContent");
		List<ActMsg> actMsgList = actMsgService.pageRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("unReadCount", actMsgService.countUnRead(uid));
		model.addAttribute("citys", com.juzhai.passport.InitData.CITY_MAP);
		model.addAttribute("point", accountService.queryPoint(uid));
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
	public AjaxResult openMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer curPage,
			Integer curIndex,String type) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (curPage != null && curIndex != null&&!StringUtils.isEmpty(type)) {
				// 查询积分
				int point = accountService.queryPoint(context.getUid());
				// 判断积分余额
				int openMsgPoint = 0;
				ConsumeAction consumeAction=null;
					if(MsgType.INVITE.name().equals(type)){
						openMsgPoint=com.juzhai.account.InitData.CONSUME_ACTION_RULE
						.get(ConsumeAction.OPEN_MESSAGE_INVITE);
						consumeAction=ConsumeAction.OPEN_MESSAGE_INVITE;
					}else if(MsgType.RECOMMEND.name().equals(type)){
						openMsgPoint=com.juzhai.account.InitData.CONSUME_ACTION_RULE
						.get(ConsumeAction.OPEN_MESSAGE_RECOMMEND);
						consumeAction=ConsumeAction.OPEN_MESSAGE_RECOMMEND;
					}
				if (point + openMsgPoint >= 0) {
					int index = (curPage - 1) * unReadActMsgRows + curIndex;
					actMsgService.openMessage(context.getUid(), index);
						accountService.consumePoint(context.getUid(),
								ConsumeAction.OPEN_MESSAGE_INVITE);
					result.setSuccess(true);
				} else {
					// 积分余额不足
					result.setResult(false);
					result.setErrorCode("-1");
				}
			} else {
				result.setResult(false);
			}
		} catch (Exception e) {
			result.setResult(false);
		}
		return result;
	}

	@RequestMapping(value = "/reMoveMessage", method = RequestMethod.POST)
	public String reMoveMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer curPage,
			Integer curIndex, String type) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		try {
			if (curPage != null && curIndex != null) {
				int index = (curPage - 1) * unReadActMsgRows + curIndex;
				if ("unread".equals(type)) {
					actMsgService.removeUnRead(context.getUid(), index);
				} else if ("read".equals(type)) {
					actMsgService.removeRead(context.getUid(), index);
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	@RequestMapping(value = "/agreeMessage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult agreeMessage(HttpServletRequest request,
			HttpServletResponse response, Model model, Long actId,
			Long receiverId, Integer curPage, Integer curIndex)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (actId != null && receiverId != null && curPage != null
					&& curIndex != null) {
				int index = (curPage - 1) * unReadActMsgRows + curIndex;
				// 改变消息状态
				actMsgService.updateMsgStuts(context.getUid(), index);
				ActMsg msg = new ActMsg(actId, MsgType.INVITE);
				// 发送拒宅邀请
				msgMessageService.sendActMsg(context.getUid(), receiverId, msg);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping(value = "/getUnMessageCount")
	@ResponseBody
	public AjaxResult getUnMessageCount(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			long unread = actMsgService.countUnRead(context.getUid());
			result.setSuccess(true);
			result.setResult(unread);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}
}
