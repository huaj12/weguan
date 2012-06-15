package com.juzhai.home.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.InterestUserView;
import com.juzhai.home.controller.view.VisitorView;
import com.juzhai.home.service.IUserStatusService;
import com.juzhai.home.service.IVisitUserService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.view.UserPreferenceView;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.UserPreference;
import com.juzhai.preference.service.IPreferenceService;
import com.juzhai.preference.service.IUserPreferenceService;

@Controller
@RequestMapping(value = "home")
public class UserController extends BaseController {

	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IUserStatusService userStatusService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPreferenceService preferenceService;
	@Autowired
	private IUserPreferenceService userPreferenceService;
	@Autowired
	private IVisitUserService visitUserService;
	@Value("${web.user.home.post.rows}")
	private int webUserHomePostRows;
	@Value("${web.my.post.max.rows}")
	private int webMyPostMaxRows;
	@Value("${web.interest.user.max.rows}")
	private int webInterestUserMaxRows;
	@Value("${web.interest.me.max.rows}")
	private int webInterestMeMaxRows;
	@Value("${user.page.recommend.user.count}")
	private int userPageRecommendUserCount;
	@Value("${visitor.widget.user.count}")
	private int visitorWidgetUserCount;
	@Value("${web.visit.user.max.rows}")
	private int webVisitUserMaxRows;
	@Value("${recommend.user.count}")
	private int recommendUserCount;

	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	public String userHome(HttpServletRequest request, Model model,
			@PathVariable long uid) throws NeedLoginException {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin() && context.getUid() == uid) {
			return myPosts(request, model);
		} else {
			try {
				showUserPageRight(context, uid, model);
			} catch (JuzhaiException e) {
				return error_404;
			}
			// 前三条
			int postCount = postService.countUserPost(uid);
			model.addAttribute("showMore", postCount > webUserHomePostRows);
			List<Post> postList = postService.listUserPost(uid, null, 0,
					webUserHomePostRows);
			List<PostView> postViewList = assembleUserPostViewList(context,
					postList);
			model.addAttribute("postViewList", postViewList);

			// 微博
			model.addAttribute("userStatusList",
					userStatusService.listUserStatus(uid));
			TpUser tpUser = tpUserService.getTpUserByUid(uid);
			model.addAttribute("tpUser", tpUser);
			if (context.hasLogin()) {
				isShield(model, context.getUid(), uid);
			}
			// 获取用户偏好
			getUserPreference(model, uid);
			recommendUserWidget(context.getUid(), recommendUserCount, model);
			return "web/home/user_home";
		}
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String myPosts(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		return pageMyPosts(request, model, 1);
	}

	@RequestMapping(value = "/posts/{page}", method = RequestMethod.GET)
	public String pageMyPosts(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		// int totalCount = (Integer) model.asMap().get("postCount");
		int totalCount = postService.countUserPost(context.getUid());
		PagerManager pager = new PagerManager(page, webMyPostMaxRows,
				totalCount);
		List<Post> postList = postService.listUserPost(context.getUid(), null,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("pager", pager);
		return "web/home/post/posts";
	}

	@RequestMapping(value = "/interests", method = RequestMethod.GET)
	public String myInterests(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		return pageMyInterests(request, model, 1);
	}

	@RequestMapping(value = "/interests/{page}", method = RequestMethod.GET)
	public String pageMyInterests(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		// int totalCount = (Integer) model.asMap().get("interestCount");
		int totalCount = interestUserService
				.countInterestUser(context.getUid());
		PagerManager pager = new PagerManager(page, webInterestUserMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService.listInterestUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		assembleInterestUserView(model, context, profileList, true);
		model.addAttribute("pager", pager);
		return "web/home/interest/interests";
	}

	@RequestMapping(value = "/interestMes", method = RequestMethod.GET)
	public String myInterestMes(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		return pageMyInterestMes(request, model, 1);
	}

	@RequestMapping(value = "/interestMes/{page}", method = RequestMethod.GET)
	public String pageMyInterestMes(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		// int totalCount = (Integer) model.asMap().get("interestMeCount");
		int totalCount = interestUserService.countInterestMeUser(context
				.getUid());
		PagerManager pager = new PagerManager(page, webInterestMeMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService
				.listInterestMeUser(context.getUid(), pager.getFirstResult(),
						pager.getMaxResult());
		assembleInterestUserView(model, context, profileList, null);
		model.addAttribute("pager", pager);
		return "web/home/interest/interest_mes";
	}

	private void assembleInterestUserView(Model model, UserContext context,
			List<ProfileCache> profileList, Boolean hasInterest) {
		List<Long> uidList = new ArrayList<Long>();
		for (ProfileCache profile : profileList) {
			uidList.add(profile.getUid());
		}
		Map<Long, Post> userLatestPostMap = postService
				.getMultiUserLatestPosts(uidList);
		List<InterestUserView> interestUserViewList = new ArrayList<InterestUserView>();
		for (ProfileCache profileCache : profileList) {
			InterestUserView view = new InterestUserView();
			view.setProfileCache(profileCache);
			view.setHasInterest(hasInterest != null ? hasInterest
					: interestUserService.isInterest(context.getUid(),
							profileCache.getUid()));
			view.setOnline(loginService.isOnline(profileCache.getUid()));
			view.setLatestPost(userLatestPostMap.get(profileCache.getUid()));
			interestUserViewList.add(view);
		}
		model.addAttribute("interestUserViewList", interestUserViewList);
	}

	@RequestMapping(value = "/{uid}/posts", method = RequestMethod.GET)
	public String userPosts(HttpServletRequest request, Model model,
			@PathVariable long uid) {
		return pageUserPosts(request, model, uid, 1);
	}

	@RequestMapping(value = "/{uid}/posts/{page}", method = RequestMethod.GET)
	public String pageUserPosts(HttpServletRequest request, Model model,
			@PathVariable long uid, @PathVariable int page) {
		UserContext context = null;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
		}
		if (context != null && context.getUid() == uid) {
			return "redirect:/home/posts";
		}
		try {
			showUserPageRight(context, uid, model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		pageUserPosts(context, uid, page, model);
		return "web/home/post/user_posts";
	}

	private void pageUserPosts(UserContext context, long uid, int page,
			Model model) {
		int totalCount = postService.countUserPost(uid);
		PagerManager pager = new PagerManager(page, webMyPostMaxRows,
				totalCount);
		List<Post> postList = postService.listUserPost(uid, null,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("pager", pager);
	}

	private List<PostView> assembleUserPostViewList(UserContext context,
			List<Post> postList) {
		List<PostView> postViewList = new ArrayList<PostView>();
		for (Post post : postList) {
			PostView postView = new PostView();
			postView.setPost(post);
			if (context != null && context.hasLogin()
					&& context.getUid() != post.getCreateUid()) {
				postView.setHasResponse(postService.isResponsePost(
						context.getUid(), post.getId()));
			}
			postViewList.add(postView);
		}
		return postViewList;
	}

	private void showUserPageRight(UserContext context, long uid, Model model)
			throws JuzhaiException {
		// if (null == queryProfile(uid, model)) {
		// throw new JuzhaiException(JuzhaiException.ILLEGAL_OPERATION);
		// }
		Profile profile = profileService.getProfile(uid);
		if (null == profile) {
			throw new JuzhaiException(JuzhaiException.ILLEGAL_OPERATION);
		}
		model.addAttribute("profile", profile);
		if (context == null || context.getUid() != uid) {
			model.addAttribute("online", loginService.isOnline(uid));
			if (context != null && context.hasLogin()) {
				model.addAttribute("isInterest",
						interestUserService.isInterest(context.getUid(), uid));
				// 添加来访者
				visitUserService.addVisitUser(uid, context.getUid());
			}
		} else {
			visitUserWidget(model, context, visitorWidgetUserCount);
			// 可能感兴趣
			recommendUserWidget(context.getUid(), userPageRecommendUserCount,
					model);
		}
	}

	@RequestMapping(value = "/preference", method = RequestMethod.GET)
	public String myPreference(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		getUserPreference(model, context.getUid());
		return "web/home/preference/preference";
	}

	private void getUserPreference(Model model, long uid) {
		List<Preference> preferences = preferenceService
				.listCacheShowPreference();
		List<UserPreference> userPreferences = userPreferenceService
				.listUserPreference(uid);
		List<UserPreferenceView> views = userPreferenceService
				.convertToUserHomePreferenceView(userPreferences, preferences);
		model.addAttribute("preferenceListviews", views);
	}

	@RequestMapping(value = "/visitors", method = RequestMethod.GET)
	public String myVisitors(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		return pageMyVisitors(request, model, 1);
	}

	@RequestMapping(value = "/visitors/{page}", method = RequestMethod.GET)
	public String pageMyVisitors(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		int totalCount = visitUserService.countVisitUsers(context.getUid());
		PagerManager pager = new PagerManager(page, webVisitUserMaxRows,
				totalCount);
		List<VisitorView> visitorViewList = visitUserService.listVisitUsers(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		List<Long> uidList = new ArrayList<Long>();
		for (VisitorView visitorView : visitorViewList) {
			uidList.add(visitorView.getProfileCache().getUid());
		}
		Map<Long, Post> userLatestPostMap = postService
				.getMultiUserLatestPosts(uidList);
		for (VisitorView visitorView : visitorViewList) {
			visitorView.setHasInterest(interestUserService.isInterest(
					context.getUid(), visitorView.getProfileCache().getUid()));
			visitorView.setOnline(loginService.isOnline(visitorView
					.getProfileCache().getUid()));
			visitorView.setLatestPost(userLatestPostMap.get(visitorView
					.getProfileCache().getUid()));
		}
		model.addAttribute("visitorViewList", visitorViewList);
		model.addAttribute("isVisitorPage", true);
		model.addAttribute("pager", pager);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		return "web/home/visitor/visitors";
	}
}
