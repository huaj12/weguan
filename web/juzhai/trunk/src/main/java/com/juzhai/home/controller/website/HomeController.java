package com.juzhai.home.controller.website;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IRecommendPostService;

@Controller
@RequestMapping(value = "home")
public class HomeController extends BaseController {

	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IRecommendPostService recommendPostService;
	@Value("${web.home.post.max.rows}")
	private int webHomePostMaxRows;
	@Value("${web.home.right.user.rows}")
	private int webHomeRightUserRows;

	// @Value("${web.home.right.idea.rows}")
	// private int webHomeRightIdeaRows;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		return showNewPosts(request, model, 0, "all", 1);
	}

	@RequestMapping(value = "/showNewPosts/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String oldShowNewPosts(HttpServletRequest request, Model model,
			@PathVariable long cityId, @PathVariable String genderType,
			@PathVariable int page) {
		return "redirect:/home/showposts/0_" + genderType + "/1";
	}

	@RequestMapping(value = "/showposts/{townId}_{genderType}/{page}", method = RequestMethod.GET)
	public String showNewPosts(HttpServletRequest request, Model model,
			@PathVariable long townId, @PathVariable String genderType,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long cityId = 0L;
		ProfileCache loginUser = getLoginUserCache(request);
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		showHomeRight(context, model);
		Integer gender = null;
		if (StringUtils.equals(genderType, "male")) {
			gender = 1;
		} else if (StringUtils.equals(genderType, "female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countNewestPost(context.getUid(), cityId, townId,
						gender));
		List<Post> postList = null;
		if (pager.getTotalResults() > 0) {
			postList = postService.listNewestPost(context.getUid(), cityId,
					townId, gender, pager.getFirstResult(),
					pager.getMaxResult());
		} else {
			List<Post> recommendPostList = recommendPostService
					.listRecommendPost();
			postList = new ArrayList<Post>();
			if (CollectionUtils.isNotEmpty(recommendPostList)) {
				for (Post post : recommendPostList) {
					if (post.getCreateUid() != context.getUid()) {
						postList.add(post);
					}
				}
			}
		}
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryType", "showposts");
		model.addAttribute("cityId", cityId);
		model.addAttribute("townId", townId);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "home");
		loadCategoryList(model);
		loadFaces(model);
		return "web/home/index/home";
	}

	@RequestMapping(value = "/showRespPosts/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String oldShowRespPosts(HttpServletRequest request, Model model,
			@PathVariable long cityId, @PathVariable String genderType,
			@PathVariable int page) {
		return "redirect:/home/showrposts/" + genderType + "/1";
	}

	@RequestMapping(value = "/showrposts/{genderType}/{page}", method = RequestMethod.GET)
	public String showRespPosts(HttpServletRequest request, Model model,
			@PathVariable String genderType, @PathVariable int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeRight(context, model);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countResponsePost(context.getUid(), null, gender));
		List<Post> postList = postService.listResponsePost(context.getUid(),
				null, gender, pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryType", "showrposts");
		// model.addAttribute("cityId", cityId);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "home");
		loadCategoryList(model);
		loadFaces(model);
		return "web/home/index/home";
	}

	@RequestMapping(value = "/showIntPosts/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String oldShowIntPosts(HttpServletRequest request, Model model,
			@PathVariable long cityId, @PathVariable String genderType,
			@PathVariable int page) throws NeedLoginException {
		return "redirect:/home/showiposts/" + genderType + "/1";
	}

	@RequestMapping(value = "/showiposts/{genderType}/{page}", method = RequestMethod.GET)
	public String showIntPosts(HttpServletRequest request, Model model,
			@PathVariable String genderType, @PathVariable int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		showHomeRight(context, model);
		Integer gender = null;
		if (genderType.equals("male")) {
			gender = 1;
		} else if (genderType.equals("female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countInterestUserPost(context.getUid(), null,
						gender));
		List<Post> postList = postService.listInterestUserPost(
				context.getUid(), null, gender, pager.getFirstResult(),
				pager.getMaxResult());
		List<PostView> postViewList = assembleUserPostViewList(context,
				postList);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryType", "showiposts");
		// model.addAttribute("cityId", cityId);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "home");
		loadCategoryList(model);
		loadFaces(model);
		return "web/home/index/home";
	}

	private void showHomeRight(UserContext context, Model model) {
		showHomeLogo(context, model);
		// ideaWidget(context, cityId, model, webHomeRightIdeaRows);
		newUserWidget(0L, model, webHomeRightUserRows);
	}

	private List<PostView> assembleUserPostViewList(UserContext context,
			List<Post> postList) {
		List<PostView> postViewList = new ArrayList<PostView>();
		for (Post post : postList) {
			PostView postView = new PostView();
			postView.setPost(post);
			postView.setProfileCache(profileService.getProfileCacheByUid(post
					.getCreateUid()));
			if (context != null && context.getUid() > 0) {
				postView.setHasResponse(postService.isResponsePost(
						context.getUid(), post.getId()));
				postView.setHasInterest(interestUserService.isInterest(
						context.getUid(), post.getCreateUid()));
			}
			postViewList.add(postView);
		}
		return postViewList;
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
