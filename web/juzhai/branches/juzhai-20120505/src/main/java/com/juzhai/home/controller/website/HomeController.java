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
	@Value("${web.home.post.max.rows}")
	private int webHomePostMaxRows;
	@Value("${web.home.right.user.rows}")
	private int webHomeRightUserRows;

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
		UserContext context = checkLoginForWeb(request);
		long cityId = 0L;
		ProfileCache loginUser = getLoginUserCache(request);
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		showHomeRight(context, model);
		Integer gender = getGender(genderType);
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
		List<PostView> postViewList = postViewHelper.assembleUserPostViewList(
				context, postList);
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
		showHomeRight(context, model);
		Integer gender = getGender(genderType);
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
		Integer gender = getGender(genderType);
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

	private void showHomeRight(UserContext context, Model model) {
		showHomeLogo(context, model);
		// ideaWidget(context, cityId, model, webHomeRightIdeaRows);
		newUserWidget(0L, model, webHomeRightUserRows);
	}
}
