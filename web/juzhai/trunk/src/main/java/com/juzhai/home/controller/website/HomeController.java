package com.juzhai.home.controller.website;

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

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.InterestUserView;
import com.juzhai.home.service.IUserFreeDateService;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.login.ILoginService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

@Controller
@RequestMapping(value = "home")
public class HomeController extends BaseController {

	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IUserFreeDateService userFreeDateService;
	@Autowired
	private IPostService postService;
	@Value("${web.home.post.max.rows}")
	private int webHomePostMaxRows;
	@Value("${web.user.home.post.rows}")
	private int webUserHomePostRows;
	@Value("${web.my.post.max.rows}")
	private int webMyPostMaxRows;
	@Value("${web.interest.user.max.rows}")
	private int webInterestUserMaxRows;
	@Value("${web.interest.me.max.rows}")
	private int webInterestMeMaxRows;
	@Value("${interest.user.show.act.count}")
	private int interestUserShowActCount;
	@Value("${web.home.right.idea.rows}")
	private int webHomeRightIdeaRows;
	@Value("${web.home.right.user.rows}")
	private int webHomeRightUserRows;

	// @RequestMapping(value = { "/acts", "/", "" }, method = RequestMethod.GET)
	// public String myActs(HttpServletRequest request, Model model)
	// throws NeedLoginException {
	// checkLoginForWeb(request);
	// return pageMyActs(request, model, 1);
	// }
	//
	// @RequestMapping(value = "/acts/{page}", method = RequestMethod.GET)
	// public String pageMyActs(HttpServletRequest request, Model model,
	// @PathVariable int page) throws NeedLoginException {
	// UserContext context = checkLoginForWeb(request);
	// try {
	// showHomeHeader(context, context.getUid(), model);
	// } catch (JuzhaiException e) {
	// return error_404;
	// }
	// int totalCount = (Integer) model.asMap().get("actCount");
	// PagerManager pager = new PagerManager(page, webMyActMaxRows, totalCount);
	// List<UserActView> userActViewList = userActService.pageUserActView(
	// context.getUid(), pager.getFirstResult(), pager.getMaxResult());
	// model.addAttribute("userActViewList", userActViewList);
	// model.addAttribute("pager", pager);
	// return "web/home/act/acts";
	// }

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		return showNewPosts(request, model, 0L, "all", 1);
	}

	@RequestMapping(value = "/showNewPosts/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String showNewPosts(HttpServletRequest request, Model model,
			@PathVariable long cityId, @PathVariable String genderType,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		queryProfile(context.getUid(), model);
		showHomeRight(context, cityId, model);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countNewestPost(context.getUid(), cityId, gender));
		List<Post> postList = postService.listNewestPost(context.getUid(),
				cityId, gender, pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList, true, true);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryType", "showNewPosts");
		model.addAttribute("cityId", cityId);
		model.addAttribute("genderType", genderType);
		return "web/home/index/home";
	}

	@RequestMapping(value = "/showRespPosts/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String showRespPosts(HttpServletRequest request, Model model,
			@PathVariable long cityId, @PathVariable String genderType,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		queryProfile(context.getUid(), model);
		showHomeRight(context, cityId, model);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countResponsePost(context.getUid(), cityId, gender));
		List<Post> postList = postService.listResponsePost(context.getUid(),
				cityId, gender, pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList, true, true);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryType", "showRespPosts");
		model.addAttribute("cityId", cityId);
		model.addAttribute("genderType", genderType);
		return "web/home/index/home";
	}

	@RequestMapping(value = "/showIntPosts/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String showIntPosts(HttpServletRequest request, Model model,
			@PathVariable long cityId, @PathVariable String genderType,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		queryProfile(context.getUid(), model);
		showHomeRight(context, cityId, model);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countInterestUserPost(context.getUid(), cityId,
						gender));
		List<Post> postList = postService.listInterestUserPost(
				context.getUid(), cityId, gender, pager.getFirstResult(),
				pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList, true, true);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryType", "showIntPosts");
		model.addAttribute("cityId", cityId);
		model.addAttribute("genderType", genderType);
		return "web/home/index/home";
	}

	private void showHomeRight(UserContext context, long cityId, Model model) {
		// TODO 是否要改成未发布的最新idea
		List<Idea> ideaList = ideaService.listIdeaByCity(cityId,
				ShowIdeaOrder.HOT_TIME, 0, webHomeRightIdeaRows);
		List<IdeaView> ideaViewList = new ArrayList<IdeaView>();
		for (Idea idea : ideaList) {
			IdeaView ideaView = new IdeaView();
			ideaView.setIdea(idea);
			ideaView.setHasUsed(ideaService.isUseIdea(context.getUid(),
					idea.getId()));
			ideaViewList.add(ideaView);
		}
		model.addAttribute("ideaViewList", ideaViewList);

		model.addAttribute("profileList", profileService
				.listProfileByCityIdOrderCreateTime(cityId, 0,
						webHomeRightUserRows));
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
		int totalCount = (Integer) model.asMap().get("postCount");
		PagerManager pager = new PagerManager(page, webMyPostMaxRows,
				totalCount);
		List<Post> postList = postService.listUserPost(context.getUid(),
				pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("postList", postList);
		model.addAttribute("pager", pager);
		return "web/home/post/posts";
	}

	@RequestMapping(value = "/interests/{page}", method = RequestMethod.GET)
	public String myInterests(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		int totalCount = (Integer) model.asMap().get("interestCount");
		PagerManager pager = new PagerManager(page, webInterestUserMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService.listInterestUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		assembleInterestUserView(model, context, profileList, true);
		model.addAttribute("pager", pager);
		return "web/home/interest/interests";
	}

	@RequestMapping(value = "/interestMes/{page}", method = RequestMethod.GET)
	public String myInterestMe(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			showUserPageRight(context, context.getUid(), model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		int totalCount = (Integer) model.asMap().get("interestMeCount");
		PagerManager pager = new PagerManager(page, webInterestMeMaxRows,
				totalCount);
		List<ProfileCache> profileList = interestUserService
				.listInterestMeUser(context.getUid(), pager.getFirstResult(),
						pager.getMaxResult());
		assembleInterestUserView(model, context, profileList, null);
		model.addAttribute("pager", pager);
		if (page <= 1) {
			noticeService.emptyNotice(context.getUid(), NoticeType.INTEREST_ME);
		}
		return "web/home/interest/interest_mes";
	}

	private void assembleInterestUserView(Model model, UserContext context,
			List<ProfileCache> profileList, Boolean hasInterest) {
		List<InterestUserView> interestUserViewList = new ArrayList<InterestUserView>();
		for (ProfileCache profileCache : profileList) {
			InterestUserView view = new InterestUserView();
			view.setProfileCache(profileCache);
			view.setUserActViewList(userActService.pageUserActView(
					profileCache.getUid(), 0, interestUserShowActCount));
			// view.setHasDating(datingService.hasDating(context.getUid(),
			// profileCache.getUid()));
			view.setHasInterest(hasInterest != null ? hasInterest
					: interestUserService.isInterest(context.getUid(),
							profileCache.getUid()));
			view.setOnline(loginService.isOnline(profileCache.getUid()));
			view.setFreeDateList(userFreeDateService
					.userFreeDateList(profileCache.getUid()));
			view.setLatestPost(postService.getUserLatestPost(profileCache
					.getUid()));
			interestUserViewList.add(view);
		}
		model.addAttribute("interestUserViewList", interestUserViewList);
	}

	// @RequestMapping(value = "/datings/accept/{page}", method =
	// RequestMethod.GET)
	// public String myAcceptDatings(HttpServletRequest request, Model model,
	// @PathVariable int page) throws NeedLoginException {
	// UserContext context = checkLoginForWeb(request);
	// try {
	// showHomeHeader(context, context.getUid(), model);
	// } catch (JuzhaiException e) {
	// return error_404;
	// }
	// PagerManager pager = new PagerManager(page, webDatingMaxRows,
	// datingService.countDating(context.getUid(),
	// DatingResponse.ACCEPT.getValue()));
	// List<Dating> datingList = datingService.listDating(context.getUid(),
	// DatingResponse.ACCEPT.getValue(), pager.getFirstResult(),
	// pager.getMaxResult());
	// assembleDatingView(model, datingList, false);
	// model.addAttribute("pager", pager);
	// model.addAttribute("response", "accept");
	// if (page <= 1) {
	// noticeService.emptyNotice(context.getUid(),
	// NoticeType.ACCEPT_DATING);
	// }
	// return "web/home/dating/datings";
	// }
	//
	// @RequestMapping(value = "/datings/{page}", method = RequestMethod.GET)
	// public String myDatings(HttpServletRequest request, Model model,
	// @PathVariable int page) throws NeedLoginException {
	// UserContext context = checkLoginForWeb(request);
	// try {
	// showHomeHeader(context, context.getUid(), model);
	// } catch (JuzhaiException e) {
	// return error_404;
	// }
	// int totalCount = (Integer) model.asMap().get("datingCount");
	// PagerManager pager = new PagerManager(page, webDatingMaxRows,
	// totalCount);
	// List<Dating> datingList = datingService.listDating(context.getUid(),
	// null, pager.getFirstResult(), pager.getMaxResult());
	// assembleDatingView(model, datingList, false);
	// model.addAttribute("pager", pager);
	// return "web/home/dating/datings";
	// }
	//
	// @RequestMapping(value = "/datingMes/{page}", method = RequestMethod.GET)
	// public String myDatingMe(HttpServletRequest request, Model model,
	// @PathVariable int page) throws NeedLoginException {
	// UserContext context = checkLoginForWeb(request);
	// try {
	// showHomeHeader(context, context.getUid(), model);
	// } catch (JuzhaiException e) {
	// return error_404;
	// }
	// int totalCount = (Integer) model.asMap().get("datingMeCount");
	// PagerManager pager = new PagerManager(page, webDatingMeMaxRows,
	// totalCount);
	// List<Dating> datingList = datingService.listDatingMe(context.getUid(),
	// pager.getFirstResult(), pager.getMaxResult());
	// assembleDatingView(model, datingList, true);
	// model.addAttribute("pager", pager);
	// if (page <= 1) {
	// noticeService.emptyNotice(context.getUid(), NoticeType.DATING_ME);
	// }
	// return "web/home/dating/dating_mes";
	// }
	//
	// private void assembleDatingView(Model model, List<Dating> datingList,
	// boolean isShowDatingMe) {
	// List<DatingView> datingViewList = new ArrayList<DatingView>();
	// for (Dating dating : datingList) {
	// DatingView view = new DatingView(dating,
	// actService.getActById(dating.getActId()),
	// profileService.getProfileCacheByUid(isShowDatingMe ? dating
	// .getStarterUid() : dating.getReceiverUid()));
	// datingViewList.add(view);
	// }
	// model.addAttribute("datingViewList", datingViewList);
	// }

	// @RequestMapping(value = { "/{uid}", "/{uid}/acts" }, method =
	// RequestMethod.GET)
	// public String userHome(HttpServletRequest request, Model model,
	// @PathVariable long uid) {
	// return userHomeActs(request, model, uid, 1);
	// }

	// @RequestMapping(value = "/{uid}/acts/{page}", method = RequestMethod.GET)
	// public String userHomeActs(HttpServletRequest request, Model model,
	// @PathVariable long uid, @PathVariable int page) {
	// UserContext context = null;
	// try {
	// context = checkLoginForWeb(request);
	// } catch (NeedLoginException e) {
	// }
	// if (context != null && context.getUid() == uid) {
	// return "redirect:/home";
	// }
	// try {
	// showHomeHeader(context, uid, model);
	// } catch (JuzhaiException e) {
	// return error_404;
	// }
	// pageOtherUserActs(context, uid, page, model);
	// return "web/home/act/user_acts";
	// }
	//
	// private void pageOtherUserActs(UserContext context, long uid, int page,
	// Model model) {
	// int totalCount = (Integer) model.asMap().get("actCount");
	// PagerManager pager = new PagerManager(page, webMyActMaxRows, totalCount);
	// List<UserActView> userActViewList = userActService.pageUserActView(uid,
	// pager.getFirstResult(), pager.getMaxResult());
	// if (context != null && context.getUid() > 0) {
	// for (UserActView userActView : userActViewList) {
	// userActView.setHasUsed(userActService.hasAct(context.getUid(),
	// userActView.getAct().getId()));
	// }
	// }
	// model.addAttribute("userActViewList", userActViewList);
	// model.addAttribute("pager", pager);
	// }

	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	public String userHome(HttpServletRequest request, Model model,
			@PathVariable long uid) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context != null && context.getUid() == uid) {
			return "redirect:/home";
		}
		try {
			showUserPageRight(context, uid, model);
		} catch (JuzhaiException e) {
			return error_404;
		}
		// 前三条
		int postCount = postService.countUserPost(uid);
		model.addAttribute("showMore", postCount > webUserHomePostRows);
		List<Post> postList = postService.listUserPost(uid, 0,
				webUserHomePostRows);
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList, false, false);
		model.addAttribute("postViewList", postViewList);

		// TODO 微博

		return "web/home/user_home";
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
		List<Post> postList = postService.listUserPost(uid,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList, false, false);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("pager", pager);
	}

	private List<PostView> assembleUserPostViewList(UserContext context,
			List<Post> postList, boolean needProfile, boolean needHasInterest) {
		List<PostView> postViewList = new ArrayList<PostView>();
		for (Post post : postList) {
			PostView postView = new PostView();
			postView.setPost(post);
			if (needProfile) {
				postView.setProfileCache(profileService
						.getProfileCacheByUid(post.getCreateUid()));
			}
			if (context != null && context.getUid() > 0) {
				postView.setHasResponse(postService.isResponsePost(
						context.getUid(), post.getId()));
				if (needHasInterest) {
					postView.setHasInterest(interestUserService.isInterest(
							context.getUid(), post.getCreateUid()));
				}
			}
			postViewList.add(postView);
		}
		return postViewList;
	}

	private void showUserPageRight(UserContext context, long uid, Model model)
			throws JuzhaiException {
		if (null == queryProfile(uid, model)) {
			throw new JuzhaiException(JuzhaiException.ILLEGAL_OPERATION);
		}
		if (context == null || context.getUid() != uid) {
			model.addAttribute("online", loginService.isOnline(uid));
			if (context != null && context.hasLogin()) {
				model.addAttribute("isInterest",
						interestUserService.isInterest(context.getUid(), uid));
			}
		} else {
			model.addAttribute("interestCount",
					interestUserService.countInterestUser(uid));
			model.addAttribute("interestMeCount",
					interestUserService.countInterestMeUser(uid));
			model.addAttribute("postCount", postService.countUserPost(uid));
		}
	}

	// private void showHomeHeader(UserContext context, long uid, Model model)
	// throws JuzhaiException {
	// if (null == queryProfile(uid, model)) {
	// throw new JuzhaiException(JuzhaiException.ILLEGAL_OPERATION);
	// }
	// model.addAttribute("actCount",
	// userActService.countUserActByUid(uid));
	// List<Date> freeDateList = userFreeDateService.userFreeDateList(uid);
	// model.addAttribute("freeDateList", freeDateList);
	// if (context == null || context.getUid() != uid) {
	// model.addAttribute("online", loginService.isOnline(uid));
	// if (context != null && context.hasLogin()) {
	// model.addAttribute("isInterest",
	// interestUserService.isInterest(context.getUid(), uid));
	// Dating dating = datingService
	// .fetchDating(context.getUid(), uid);
	// if (null != dating) {
	// model.addAttribute("datingView", new DatingView(dating,
	// actService.getActById(dating.getActId()), null));
	// }
	// }
	// } else {
	// List<Long> freeDateLongList = new ArrayList<Long>(
	// freeDateList.size());
	// for (Date freeDate : freeDateList) {
	// freeDateLongList.add(freeDate.getTime());
	// }
	// List<DateView> dateViewList = new ArrayList<DateView>();
	// Date date = DateUtils.truncate(new Date(), Calendar.DATE);
	// for (int i = 0; i < 7; i++) {
	// dateViewList.add(new DateView(date, freeDateLongList
	// .contains(date.getTime())));
	// date = DateUtils.addDays(date, 1);
	// }
	// model.addAttribute("dateViewList", dateViewList);
	//
	// model.addAttribute("interestCount",
	// interestUserService.countInterestUser(uid));
	// model.addAttribute("interestMeCount",
	// interestUserService.countInterestMeUser(uid));
	// model.addAttribute("datingCount",
	// datingService.countDating(uid, null));
	// model.addAttribute("datingMeCount",
	// datingService.countDatingMe(uid));
	// }
	// }
}
