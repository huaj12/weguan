package com.juzhai.index.controller.website;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.controller.view.CategoryActView;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;
import com.juzhai.act.service.IDatingService;
import com.juzhai.act.service.IShowActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.controller.form.DateView;
import com.juzhai.home.service.IUserFreeDateService;
import com.juzhai.index.bean.ShowActOrder;
import com.juzhai.index.controller.view.ShowUserView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;

//@Controller
public class OldIndexController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IShowActService showActService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IDatingService datingService;
	@Autowired
	private IUserFreeDateService userFreeDateService;
	@Autowired
	private IFriendService friendService;
	@Value("${web.show.category.size}")
	private int webShowCategorySize;
	@Value("${web.show.acts.max.rows}")
	private int webShowActsMaxRows;
	@Value("${web.show.users.max.rows}")
	private int webShowUsersMaxRows;
	@Value("${show.user.show.act.count}")
	private int showUserShowActCount;
	@Value("${show.follow.count}")
	private int showFollowCount;
	@Value("${show.invite.users.max.rows}")
	private int showInviteUsersMaxRows;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return showActs(request, model);
	}

	@RequestMapping(value = "/showActs", method = RequestMethod.GET)
	public String showActs(HttpServletRequest request, Model model) {
		return pageShowActs(request, model, ShowActOrder.HOT_TIME.getType(),
				0L, 1);
	}

	@RequestMapping(value = "/showActs/{orderType}_{categoryId}/{page}", method = RequestMethod.GET)
	public String pageShowActs(HttpServletRequest request, Model model,
			@PathVariable String orderType, @PathVariable long categoryId,
			@PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		List<Category> categoryList = categoryService
				.listCategories(webShowCategorySize);
		ShowActOrder order = ShowActOrder.getShowActOrderByType(orderType);
		long cityId = fetchCityId(request);
		PagerManager pager = new PagerManager(page, webShowActsMaxRows,
				showActService.countShowActs(cityId, categoryId));
		List<Act> actList = showActService.listShowActs(cityId, categoryId,
				order, pager.getFirstResult(), pager.getMaxResult());
		List<CategoryActView> categoryActViewList = new ArrayList<CategoryActView>();
		for (Act act : actList) {
			CategoryActView view = new CategoryActView();
			view.setAct(act);
			if (context.hasLogin()) {
				view.setHasUsed(userActService.hasAct(context.getUid(),
						act.getId()));
			}
			categoryActViewList.add(view);
		}
		model.addAttribute("categoryActViewList", categoryActViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("orderType", orderType);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("pageType", "cqw");
		return "web/index/cqw/show_acts";
	}

	@RequestMapping(value = "/showusers", method = RequestMethod.GET)
	public String showUsers(HttpServletRequest request, Model model) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			freeDate(context.getUid(), model);
		}
		String genderType = "all";
		ProfileCache loginUser = getLoginUserCache(request);
		if (null != loginUser && loginUser.getGender() != null) {
			if (loginUser.getGender() == 0) {
				genderType = "male";
			} else {
				genderType = "female";
			}
		}
		return pageShowUsers(request, model, genderType, 1);
	}

	private void freeDate(long uid, Model model) {
		// List<Date> freeDateList = userFreeDateService.userFreeDateList(uid);
		int freeDateCount = userFreeDateService.countFreeDate(uid);
		if (freeDateCount <= 0) {
			List<DateView> dateViewList = new ArrayList<DateView>();
			Date date = DateUtils.truncate(new Date(), Calendar.DATE);
			for (int i = 0; i < 7; i++) {
				dateViewList.add(new DateView(date, false));
				date = DateUtils.addDays(date, 1);
			}
			model.addAttribute("dateViewList", dateViewList);
			model.addAttribute("setFreeDate", true);
		}
	}

	@RequestMapping(value = "/showusers/{genderType}/{page}", method = RequestMethod.GET)
	public String pageShowUsers(HttpServletRequest request, Model model,
			@PathVariable String genderType, @PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		long cityId = fetchCityId(request);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		List<Long> exceptUids = null;
		if (context.hasLogin()) {
			exceptUids = new ArrayList<Long>();
			exceptUids.add(context.getUid());
		}
		PagerManager pager = new PagerManager(page, webShowUsersMaxRows,
				profileService.countProfile(null, gender, cityId, exceptUids));
		List<Profile> profileList = profileService
				.listProfileOrderByLoginWebTime(null, gender, cityId,
						exceptUids, pager.getFirstResult(),
						pager.getMaxResult());
		model.addAttribute("showUserViewList",
				assembleShowUserView(context, profileList));
		model.addAttribute("pager", pager);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "zbe");
		return "web/index/zbe/show_users";
	}

	private List<ShowUserView> assembleShowUserView(UserContext context,
			List<Profile> profileList) {
		List<ShowUserView> showUserViewList = new ArrayList<ShowUserView>();
		for (Profile profile : profileList) {
			ShowUserView view = new ShowUserView();
			view.setProfile(profile);
			view.setUserActViewList(userActService.pageUserActView(
					profile.getUid(), 0, showUserShowActCount));
			if (context.hasLogin()) {
				view.setHasDating(datingService.hasDating(context.getUid(),
						profile.getUid()));
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), profile.getUid()));
			}
			view.setOnline(loginService.isOnline(profile.getUid()));
			view.setFreeDateList(userFreeDateService.userFreeDateList(profile
					.getUid()));
			showUserViewList.add(view);
		}
		return showUserViewList;
	}

	@RequestMapping(value = "/showFollows", method = RequestMethod.GET)
	public String followUser(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long cityId = fetchCityId(request);
		List<Long> installFollowUids = friendService
				.listInstallFollowUids(context.getUid());
		List<Profile> profileList = profileService
				.listProfileOrderByLoginWebTime(installFollowUids, null,
						cityId, null, 0, showFollowCount);
		model.addAttribute("showUserViewList",
				assembleShowUserView(context, profileList));
		model.addAttribute("showUserCount", profileService.countProfile(
				installFollowUids, null, cityId, null));
		model.addAttribute("pageType", "zbe");

		// 邀请的人
		List<TpFriend> inviteUserList = friendService
				.getUnInstallFriends(context.getUid());
		if (CollectionUtils.isNotEmpty(inviteUserList)) {
			int toIndex = Math.min(showInviteUsersMaxRows,
					inviteUserList.size());
			model.addAttribute("inviteUserList",
					inviteUserList.subList(0, toIndex));
			PagerManager pager = new PagerManager(1, showInviteUsersMaxRows,
					inviteUserList.size());
			model.addAttribute("totalPage", pager.getTotalPage());
		} else {
			model.addAttribute("inviteUserList", Collections.emptyList());
			model.addAttribute("totalPage", 0);
		}
		return "web/index/zbe/show_follows";
	}

	@RequestMapping(value = "/pageInviteUser", method = RequestMethod.GET)
	public String pageInviteUser(HttpServletRequest request, Model model,
			int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		// 邀请的人
		List<TpFriend> inviteUserList = friendService
				.getUnInstallFriends(context.getUid());
		if (page <= 0) {
			page = 1;
		}
		if (CollectionUtils.isNotEmpty(inviteUserList)) {
			int fromIndex = (page - 1) * showInviteUsersMaxRows;
			int toIndex = Math.min(fromIndex + showInviteUsersMaxRows,
					inviteUserList.size());
			model.addAttribute("inviteUserList",
					inviteUserList.subList(fromIndex, toIndex));
		} else {
			model.addAttribute("inviteUserList", Collections.emptyList());
		}
		return "web/index/zbe/invite_user_list";
	}

	@RequestMapping(value = "/showFollows/{genderType}/{page}", method = RequestMethod.GET)
	public String pageFollowUsers(HttpServletRequest request, Model model,
			@PathVariable String genderType, @PathVariable int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long cityId = fetchCityId(request);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		List<Long> installFollowUids = friendService
				.listInstallFollowUids(context.getUid());
		PagerManager pager = new PagerManager(page, webShowUsersMaxRows,
				profileService.countProfile(installFollowUids, gender, cityId,
						null));
		List<Profile> profileList = profileService
				.listProfileOrderByLoginWebTime(installFollowUids, gender,
						cityId, null, pager.getFirstResult(),
						pager.getMaxResult());
		model.addAttribute("showUserViewList",
				assembleShowUserView(context, profileList));
		model.addAttribute("pager", pager);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "zbe");
		return "web/index/zbe/show_follows";
	}

	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request, Model model) {
		return "web/index/about_us";
	}
}
