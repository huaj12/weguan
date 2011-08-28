package com.juzhai.msg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.InitData;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.controller.view.ActMsgView;
import com.juzhai.msg.service.IActMsgService;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "msg")
public class MsgCenterController extends BaseController {

	@Autowired
	private IActMsgService actMsgService;
	@Autowired
	private IProfileService profileService;
	@Value("${unread.actmsg.rows}")
	private int unReadActMsgRows = 20;
	@Value("${read.actmsg.rows}")
	private int readActMsgRows = 20;

	@RequestMapping(value = "/showUnRead", method = RequestMethod.GET)
	public String showUnRead(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		doPageUnRead(context.getUid(), model);
		return "msg/unRead";
	}

	@RequestMapping(value = "/pageUnRead", method = RequestMethod.GET)
	@ResponseBody
	public String pageUnRead(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		doPageUnRead(context.getUid(), model);
		return "msg/unReadContent";
	}

	private void doPageUnRead(long uid, Model model) {
		List<ActMsg> actMsgList = actMsgService.pageUnRead(uid,
				unReadActMsgRows);
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("unReadCount", actMsgService.countUnRead(uid));
	}

	private List<ActMsgView> assembleActMsgView(long uid,
			List<ActMsg> actMsgList) {
		List<ActMsgView> actMsgViewList = new ArrayList<ActMsgView>(
				actMsgList.size());
		for (ActMsg actMsg : actMsgList) {
			ActMsgView actMsgView = new ActMsgView();
			actMsgView.setAct(InitData.ACT_MAP.get(actMsg.getActId()));
			actMsgView
					.setProfileCache(profileService.getProfileCacheByUid(uid));
			actMsgViewList.add(actMsgView);
		}
		return actMsgViewList;
	}

	@RequestMapping(value = "/showRead", method = RequestMethod.GET)
	public String showRead(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		doPageRead(context.getUid(), model, 1);
		return "msg/read";
	}

	@RequestMapping(value = "/pageRead", method = RequestMethod.GET)
	@ResponseBody
	public String pageRead(HttpServletRequest request, Model model, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		doPageRead(context.getUid(), model, page);
		return "msg/readContent";
	}

	private void doPageRead(long uid, Model model, int page) {
		long totalCount = actMsgService.countRead(uid);
		PagerManager pager = new PagerManager(page, readActMsgRows, Long
				.valueOf(totalCount).intValue());
		List<ActMsg> actMsgList = actMsgService.pageRead(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<ActMsgView> actMsgViewList = assembleActMsgView(uid, actMsgList);
		model.addAttribute("actMsgViewList", actMsgViewList);
		model.addAttribute("pager", pager);
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
}
