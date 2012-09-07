package com.juzhai.home.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.juzhai.common.InitData;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.controller.view.RecommendUserView;
import com.juzhai.home.controller.view.VisitorView;
import com.juzhai.home.service.IBlacklistService;
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.home.service.IInterestUserService;
import com.juzhai.home.service.IVisitUserService;
import com.juzhai.index.controller.view.CategoryView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.ISearchHotService;

public class HomeBaseController extends BaseController {

	@Autowired
	private IPostService postService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IBlacklistService blacklistService;
	@Autowired
	private IGuessYouService guessYouService;
	@Autowired
	private IVisitUserService visitUserService;
	@Autowired
	private ISearchHotService searchHotService;
	@Autowired
	private IIdeaService ideaService;

	protected void showHomeLogo(UserContext context, Model model) {
		model.addAttribute("postCount",
				postService.countUserPost(context.getUid()));
		// model.addAttribute("responseCount",
		// postService.getAllResponseCnt(context.getUid()));
		model.addAttribute("completion",
				profileService.getProfileCompletion(context.getUid()));
		model.addAttribute("interestCount",
				interestUserService.countInterestUser(context.getUid()));
	}

	protected void loadFaces(Model model) {
		model.addAttribute("faceList", InitData.FACE_MAP.values());
	}

	protected void isShield(Model model, long createUid, long shieldUid) {
		model.addAttribute("isShield",
				blacklistService.isShield(createUid, shieldUid));
	}

	protected void recommendUserWidget(long uid, int count, Model model) {
		List<Profile> list = guessYouService.recommendUsers(uid, count);
		List<RecommendUserView> recommendUserViewList = new ArrayList<RecommendUserView>(
				list.size());
		for (Profile profile : list) {
			RecommendUserView view = new RecommendUserView();
			view.setProfile(profile);
			if (uid > 0) {
				view.setHasInterest(interestUserService.isInterest(uid,
						profile.getUid()));
			}
			recommendUserViewList.add(view);
		}
		model.addAttribute("recommendUserViewList", recommendUserViewList);
	}

	protected void visitUserWidget(Model model, UserContext context, int count) {
		if (context.getUid() > 0) {
			if (!model.containsAttribute("visitorViewList")) {
				List<VisitorView> visitorViewList = visitUserService
						.listVisitUsers(context.getUid(), 0, count);
				model.addAttribute("visitorViewList", visitorViewList);
			}
		}
	}

	protected void loadCategoryList(Model model) {
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
	}

	// 获取热词
	protected void hotWordsWidget(Model model, long city, int count) {
		model.addAttribute("hots",
				searchHotService.getSearchHotByCity(city, count));
	}

	protected void newUserWidget(long cityId, Model model, int count) {
		if (count <= 0) {
			count = 12;
		}
		model.addAttribute("profileList", profileService
				.listProfileByCityIdOrderCreateTime(cityId, 0, count));
	}

	protected void hotPostWidget(Model model, Long city, int count) {
		List<Post> list = postService.listUserHotPost(city, 0, count);
		if (CollectionUtils.isNotEmpty(list)) {
			List<PostView> listView = new ArrayList<PostView>(list.size());
			for (Post post : list) {
				ProfileCache cache = profileService.getProfileCacheByUid(post
						.getCreateUid());
				PostView view = new PostView();
				view.setPost(post);
				view.setProfileCache(cache);
				listView.add(view);
			}
			model.addAttribute("userHotPostView", listView);
		}
	}

	protected void ideaCategoryWidget(Model model, Long city) {
		List<Category> categoryList = new ArrayList<Category>(
				com.juzhai.post.InitData.CATEGORY_MAP.values());
		List<CategoryView> categoryViewList = new ArrayList<CategoryView>(
				categoryList.size());
		for (Category category : categoryList) {
			int count = getCategoryTotal(true, city, category.getId());
			categoryViewList.add(new CategoryView(category, count));
		}
		model.addAttribute("categoryViewList", categoryViewList);
	}

	private int getCategoryTotal(boolean isWindow, long cityId, long categoryId) {
		if (isWindow) {
			return ideaService.countIdeaWindow(cityId, categoryId);
		} else {
			return ideaService.countIdeaByCityAndCategory(cityId, categoryId);
		}
	}
}
