package com.juzhai.home.controller.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.act.controller.form.FriendView;
import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

//@Controller
@RequestMapping(value = "app")
public class AppUserHomeController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;
	@Value("${user.act.max.rows}")
	private int userActMaxRows = 20;
	@Value("${users.same.act.count}")
	private int usersSameActCount;
	@Value("${show.friend.max.results}")
	private int showFriendMaxResults;
	@Value("${friend.recent.acts.count}")
	private int friendRecentActsCount;

	@RequestMapping(value = { "/{uid}" }, method = RequestMethod.GET)
	public String userHome(HttpServletRequest request, Model model,
			@PathVariable long uid) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		if (context.getUid() == uid) {
			return "redirect:/app/myAct";
		}
		queryProfile(uid, model);
		pageMyAct(request, model, 1, uid);
		model.addAttribute("sameActList", userActService.listUsersSameActList(
				context.getUid(), uid, usersSameActCount));
		return "app/home/user_home";
	}

	@RequestMapping(value = "/ajax/pageUserAct", method = RequestMethod.GET)
	public String pageMyAct(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "0") long uid)
			throws NeedLoginException {
		checkLoginForApp(request);
		int totalCount = userActService.countUserActByUid(uid);
		PagerManager pager = new PagerManager(page, userActMaxRows, totalCount);
		List<UserActView> userActViewList = userActService.pageUserActView(uid,
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("userActViewList", userActViewList);
		model.addAttribute("pager", pager);
		if (CollectionUtils.isNotEmpty(userActViewList) && page == 1) {
			model.addAttribute("lastUpdateTime", userActViewList.get(0)
					.getUserAct().getCreateTime());
		}
		model.addAttribute("uid", uid);
		return "app/home/user_home_act_list";
	}

	@RequestMapping(value = "/showAllFriend", method = RequestMethod.GET)
	public String showFriend(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForApp(request);
		pageFriend(request, model, 1);
		return "app/home/all_friend";
	}

	@RequestMapping(value = "/ajax/pageFriend", method = RequestMethod.GET)
	public String pageFriend(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int page)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		int totalCount = friendService.countAppFriends(context.getUid());
		PagerManager pager = new PagerManager(page, showFriendMaxResults,
				totalCount);

		List<Profile> friendList = profileService
				.listProfileByIdsOrderByLastUpdateTime(
						friendService.getAppFriends(context.getUid()),
						pager.getFirstResult(), pager.getMaxResult());
		List<FriendView> friendViewList = new ArrayList<FriendView>(
				friendList.size());
		for (Profile profile : friendList) {
			friendViewList.add(new FriendView(profile, userActService
					.getUserActFromCache(profile.getUid(),
							friendRecentActsCount), userActService
					.getUserActCountFromCache(profile.getUid())));
		}

		model.addAttribute("friendViewList", friendViewList);
		model.addAttribute("pager", pager);
		return "app/home/friend_list";
	}
}
