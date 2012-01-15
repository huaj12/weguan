package com.juzhai.act.controller.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.controller.view.ActUserView;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "app")
public class AppActController extends BaseController {

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActService actService;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private ISynonymActService synonymActService;
	@Value("${act.user.maxResult}")
	private int actUserMaxResult;

	@RequestMapping(value = "/showAct/{actId}", method = RequestMethod.GET)
	public String showAct(HttpServletRequest request, Model model,
			@PathVariable long actId, Integer friendUser)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		Act act = actService.getActById(actId);
		if (act == null) {
			return error_404;
		}
		model.addAttribute("act", act);
		model.addAttribute("hasAct",
				userActService.hasAct(context.getUid(), actId));
		model.addAttribute("isShield", synonymActService.isShieldAct(actId));

		model.addAttribute("userActCount",
				userActService.countUserActByActId(context.getTpId(), actId));
		model.addAttribute(
				"fUserActCount",
				userActService.countFriendUserActByActId(
						friendService.getAppFriends(context.getUid()), actId));
		model.addAttribute("showFriendUser", null != friendUser);
		return "app/act/act";
	}

	@RequestMapping(value = "/ajax/pageActUser", method = RequestMethod.GET)
	public String pageActUser(HttpServletRequest request, Model model,
			long actId, int page) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		PagerManager pager = new PagerManager(page, actUserMaxResult,
				userActService.countUserActByActId(context.getTpId(), actId));
		List<UserAct> userActList = userActService.listUserActByActId(
				context.getTpId(), actId, pager.getFirstResult(),
				pager.getMaxResult());
		List<ActUserView> actUserViewList = assembleActUserView(
				context.getUid(), userActList, true);
		model.addAttribute("pager", pager);
		model.addAttribute("act", actService.getActById(actId));
		model.addAttribute("actUserViewList", actUserViewList);
		model.addAttribute("pageFriend", false);
		return "app/act/act_user_list";
	}

	@RequestMapping(value = "/ajax/pageActFriend", method = RequestMethod.GET)
	public String pageActFriend(HttpServletRequest request, Model model,
			long actId, int page) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		List<Long> friendIds = friendService.getAppFriends(context.getUid());
		PagerManager pager = new PagerManager(page, actUserMaxResult,
				userActService.countFriendUserActByActId(friendIds, actId));
		List<UserAct> userActList = userActService.listFriendUserActByActId(
				friendIds, actId, pager.getFirstResult(), pager.getMaxResult());
		List<ActUserView> actUserViewList = assembleActUserView(
				context.getUid(), userActList, false);
		model.addAttribute("pager", pager);
		model.addAttribute("act", actService.getActById(actId));
		model.addAttribute("actUserViewList", actUserViewList);
		model.addAttribute("pageFriend", true);
		return "app/act/act_user_list";
	}

	@RequestMapping(value = "/ajax/inviteHer", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult inviteHer(HttpServletRequest request, Model model,
			long friendId, @RequestParam(defaultValue = "0") long actId)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		if (actId > 0) {
			msgMessageService.sendActMsg(context.getUid(), friendId,
					new ActMsg(actId, context.getUid(), ActMsg.MsgType.INVITE));
		}
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}

	private List<ActUserView> assembleActUserView(long uid,
			List<UserAct> userActList, boolean needJudgeFriend) {
		List<ActUserView> actUserViewList = new ArrayList<ActUserView>();
		for (UserAct userAct : userActList) {
			actUserViewList.add(new ActUserView(profileService
					.getProfileCacheByUid(userAct.getUid()), userAct
					.getLastModifyTime(), needJudgeFriend ? friendService
					.isAppFriend(uid, userAct.getUid()) : true));
		}
		return actUserViewList;
	}
}
