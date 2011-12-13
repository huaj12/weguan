package com.juzhai.act.controller.website;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActAd;
import com.juzhai.act.model.ActDetail;
import com.juzhai.act.model.ActLink;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "act")
public class ActController extends BaseController {

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActService actService;
	@Autowired
	private MessageSource messageSource;
	@Value("${web.act.ad.show.count}")
	private int webActAdShowCount;
	@Value("${web.act.link.show.count}")
	private int webActLinkShowCount;
	@Value("${act.user.maxResult}")
	private int actUserMaxResult;

	@ResponseBody
	@RequestMapping(value = "/addAct", method = RequestMethod.POST)
	public AjaxResult addAct(HttpServletRequest request, long actId, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			userActService.addAct(context.getUid(), actId);
			result.setSuccess(true);
		} catch (ActInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/removeAct", method = RequestMethod.POST)
	public AjaxResult removeAct(HttpServletRequest request, long actId,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		userActService.removeAct(context.getUid(), actId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = "/{actId}", method = RequestMethod.GET)
	public String showAct(HttpServletRequest request, Model model,
			@PathVariable long actId, Integer friendUser) {
		UserContext context = null;
		try {
			context = checkLoginForApp(request);
		} catch (NeedLoginException e) {
		}
		Act act = actService.getActById(actId);
		if (act == null) {
			return error_404;
		}
		ActDetail actDetail = actService.getActDetailById(actId);
		List<ActLink> actLinkList = actService.listActLinkByActId(actId,
				webActLinkShowCount);
		List<ActAd> actAdList = actService.listActAdByActId(actId,
				webActAdShowCount);
		model.addAttribute("actAdList", actAdList);
		model.addAttribute("actLinkList", actLinkList);
		model.addAttribute("actDetail", actDetail);
		model.addAttribute("act", act);
		if (context.getUid() > 0) {
			model.addAttribute("hasAct",
					userActService.hasAct(context.getUid(), actId));
		}
		// model.addAttribute("isShield", actService.isShieldAct(actId));
		model.addAttribute("userActCount",
				userActService.countUserActByActId(0, actId));
		return "app/act/act";
	}
	// @RequestMapping(value = "/ajax/pageActUser", method = RequestMethod.GET)
	// public String pageActUser(HttpServletRequest request, Model model,
	// long actId, int page) throws NeedLoginException {
	// UserContext context = checkLoginForApp(request);
	// PagerManager pager = new PagerManager(page, actUserMaxResult,
	// userActService.countUserActByActId(context.getTpId(), actId));
	// List<UserAct> userActList = userActService.listUserActByActId(
	// context.getTpId(), actId, pager.getFirstResult(),
	// pager.getMaxResult());
	// List<ActUserView> actUserViewList = assembleActUserView(
	// context.getUid(), userActList, true);
	// model.addAttribute("pager", pager);
	// model.addAttribute("act", actService.getActById(actId));
	// model.addAttribute("actUserViewList", actUserViewList);
	// model.addAttribute("pageFriend", false);
	// return "app/act/act_user_list";
	// }
	//
	// @RequestMapping(value = "/ajax/pageActFriend", method =
	// RequestMethod.GET)
	// public String pageActFriend(HttpServletRequest request, Model model,
	// long actId, int page) throws NeedLoginException {
	// UserContext context = checkLoginForApp(request);
	// List<Long> friendIds = friendService.getAppFriends(context.getUid());
	// PagerManager pager = new PagerManager(page, actUserMaxResult,
	// userActService.countFriendUserActByActId(friendIds, actId));
	// List<UserAct> userActList = userActService.listFriendUserActByActId(
	// friendIds, actId, pager.getFirstResult(), pager.getMaxResult());
	// List<ActUserView> actUserViewList = assembleActUserView(
	// context.getUid(), userActList, false);
	// model.addAttribute("pager", pager);
	// model.addAttribute("act", actService.getActById(actId));
	// model.addAttribute("actUserViewList", actUserViewList);
	// model.addAttribute("pageFriend", true);
	// return "app/act/act_user_list";
	// }
	//
	// @RequestMapping(value = "/ajax/inviteHer", method = RequestMethod.POST)
	// @ResponseBody
	// public AjaxResult inviteHer(HttpServletRequest request, Model model,
	// long friendId, @RequestParam(defaultValue = "0") long actId)
	// throws NeedLoginException {
	// UserContext context = checkLoginForApp(request);
	// if (actId > 0) {
	// msgMessageService.sendActMsg(context.getUid(), friendId,
	// new ActMsg(actId, context.getUid(), ActMsg.MsgType.INVITE));
	// }
	// AjaxResult result = new AjaxResult();
	// result.setSuccess(true);
	// return result;
	// }
	//
	// private List<ActUserView> assembleActUserView(long uid,
	// List<UserAct> userActList, boolean needJudgeFriend) {
	// List<ActUserView> actUserViewList = new ArrayList<ActUserView>();
	// for (UserAct userAct : userActList) {
	// actUserViewList.add(new ActUserView(profileService
	// .getProfileCacheByUid(userAct.getUid()), userAct
	// .getLastModifyTime(), needJudgeFriend ? friendService
	// .isAppFriend(uid, userAct.getUid()) : true));
	// }
	// return actUserViewList;
	// }

}
