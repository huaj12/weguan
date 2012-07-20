package com.juzhai.home.controller;

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
import com.juzhai.home.bean.ShowPostOrder;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.post.bean.PostResult;
import com.juzhai.post.controller.helper.PostViewHelper;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IRecommendPostService;
import com.juzhai.preference.bean.SiftTypePreference;
import com.juzhai.preference.service.IUserPreferenceService;

@Controller
@RequestMapping(value = "home")
public class HomeController extends BaseController {
	@Autowired
	private PostViewHelper postViewHelper;
	@Autowired
	private IPostService postService;
	@Autowired
	private IRecommendPostService recommendPostService;
	@Autowired
	private IUserPreferenceService userPreferenceService;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IProfileService profileService;
	@Value("${web.home.post.max.rows}")
	private int webHomePostMaxRows;
	@Value("${web.home.right.user.rows}")
	private int webHomeRightUserRows;
	@Value("${search.user.hot.rows}")
	private int searchUserHotRows;
	@Value("${recommend.user.count}")
	private int recommendUserCount;
	@Value("${user.hot.post.rows}")
	private int userHotPostRows;

	// @Value("${visitor.widget.user.count}")
	// private int visitorWidgetUserCount;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		List<String> genders = userPreferenceService.getUserAnswer(
				context.getUid(), SiftTypePreference.GENDER.getPreferenceId());
		String genderType = "all";
		if (genders != null && genders.size() == 1) {
			String gender = genders.get(0);
			if (StringUtils.equals(gender, "1")) {
				genderType = "male";
			} else if (StringUtils.equals(gender, "0")) {
				genderType = "female";
			}
		}
		return showNewPosts(request, model, 0, genderType, 1);
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
		model.addAttribute("queryType", "showposts");
		return showPosts(request, model, townId, genderType, ShowPostOrder.NEW,
				page);
	}

	// 兼容
	@RequestMapping(value = "/showposts/{townId}_{genderType}", method = RequestMethod.GET)
	public String showNewPosts(HttpServletRequest request, Model model,
			@PathVariable long townId, @PathVariable String genderType)
			throws NeedLoginException {
		// TODO (done) redirect
		return "redirect:/home/showposts/" + townId + "_" + genderType + "/1";
	}

	@RequestMapping(value = "/showoposts/{townId}_{genderType}/{page}", method = RequestMethod.GET)
	public String showOnlinePosts(HttpServletRequest request, Model model,
			@PathVariable long townId, @PathVariable String genderType,
			@PathVariable int page) throws NeedLoginException {
		model.addAttribute("queryType", "showoposts");
		return showPosts(request, model, townId, genderType,
				ShowPostOrder.ONLINE, page);
	}

	// 兼容
	@RequestMapping(value = "/showoposts/{townId}_{genderType}", method = RequestMethod.GET)
	public String showOnlinePosts(HttpServletRequest request, Model model,
			@PathVariable long townId, @PathVariable String genderType)
			throws NeedLoginException {
		// TODO (review) redirect
		return "redirect:/home/showoposts/" + townId + "_" + genderType + "/1";
	}

	public String showPosts(HttpServletRequest request, Model model,
			long townId, String genderType, ShowPostOrder order, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long cityId = 0L;
		ProfileCache loginUser = getLoginUserCache(request);
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		Integer gender = getGender(genderType);
		long excludeUid = 0;
		if (ShowPostOrder.ONLINE.getType().equals(order.getType())) {
			excludeUid = context.getUid();

		}
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countNewOrOnlinePosts(cityId, townId, gender,
						excludeUid));
		PostResult result = null;
		if (pager.getTotalResults() > 0) {
			result = postService.listNewOrOnlinePosts(cityId, townId, gender,
					order, excludeUid, pager.getFirstResult(),
					pager.getMaxResult());
		} else {
			List<Post> recommendPostList = recommendPostService
					.listRecommendPost(webHomePostMaxRows);
			List<Post> postList = new ArrayList<Post>();
			if (CollectionUtils.isNotEmpty(recommendPostList)) {
				for (Post post : recommendPostList) {
					if (post.getCreateUid() != context.getUid()) {
						postList.add(post);
					}
				}
			}
			result = new PostResult();
			result.setPosts(postList);
		}
		List<PostView> postViewList = postViewHelper.assembleUserPostViewList(
				context, result);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("cityId", cityId);
		model.addAttribute("townId", townId);
		model.addAttribute("genderType", genderType);
		model.addAttribute("pageType", "home");
		loadCategoryList(model);
		loadFaces(model);
		showHomeCommon(cityId, townId, gender, request, context, model);
		return "web/home/index/home";
	}

	private Integer getGender(String genderType) {
		Integer gender = null;
		if (StringUtils.equals(genderType, "male")) {
			gender = 1;
		} else if (StringUtils.equals(genderType, "female")) {
			gender = 0;
		}
		return gender;
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
		Integer gender = getGender(genderType);
		showHomeCommon(0l, null, gender, request, context, model);
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countResponsePost(context.getUid(), null, gender));
		List<Post> postList = postService.listResponsePost(context.getUid(),
				null, gender, pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = postViewHelper.assembleUserPostViewList(
				context, postList);
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

	// 兼容
	@RequestMapping(value = "/showrposts/{genderType}", method = RequestMethod.GET)
	public String showRespPosts(HttpServletRequest request, Model model,
			@PathVariable String genderType) throws NeedLoginException {
		// TODO (done) 重定向
		return "redirect:/home/showrposts/" + genderType + "/1";
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
		Integer gender = getGender(genderType);
		showHomeCommon(0l, null, gender, request, context, model);
		PagerManager pager = new PagerManager(page, webHomePostMaxRows,
				postService.countInterestUserPost(context.getUid(), null,
						gender));
		List<Post> postList = postService.listInterestUserPost(
				context.getUid(), null, gender, pager.getFirstResult(),
				pager.getMaxResult());
		List<PostView> postViewList = postViewHelper.assembleUserPostViewList(
				context, postList);
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

	@RequestMapping(value = "/showiposts/{genderType}", method = RequestMethod.GET)
	public String showIntPosts(HttpServletRequest request, Model model,
			@PathVariable String genderType) throws NeedLoginException {
		return "redirect:/home/showiposts/" + genderType + "/1";
	}

	private void showHomeCommon(long cityId, Long townId, Integer gender,
			HttpServletRequest request, UserContext context, Model model) {
		long uid = 0;
		ProfileCache loginUser = getLoginUserCache(request);
		if (cityId == 0) {
			if (loginUser != null && loginUser.getCity() != null) {
				cityId = loginUser.getCity();
			}
		}
		if (loginUser != null && loginUser.getUid() != null) {
			uid = loginUser.getUid();
		}
		int onlineCount = profileService.countUserOnline(cityId, townId,
				gender, uid);
		model.addAttribute("onlineCount", onlineCount);
		// showHomeLogo(context, model);
		hotWordsWidget(model, cityId, searchUserHotRows);
		newUserWidget(cityId, model, webHomeRightUserRows);
		recommendUserWidget(context.getUid(), recommendUserCount, model);
		hotPostWidget(model, cityId, userHotPostRows);
		Passport passport = passportService.getPassportByUid(context.getUid());
		if (null != passport) {
			model.addAttribute("hasNotAccount",
					!registerService.hasAccount(passport));
			model.addAttribute("hasNotActive",
					!registerService.hasActiveEmail(passport));
		}
	}
}
