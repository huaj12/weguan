package com.juzhai.mobile.post.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.ShowPostOrder;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.passport.controller.view.UserMView;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.mobile.post.controller.viewHelper.IPostMViewHelper;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IRecommendPostService;

@Controller
@RequestMapping("mobile/post")
public class PostMController extends BaseController {

	@Autowired
	private IPostService postService;
	@Autowired
	private IRecommendPostService recommendPostService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private IPostMViewHelper postMViewHelper;
	@Autowired
	private MessageSource messageSource;
	// @Value("${web.show.users.max.rows}")
	private int webShowUsersMaxRows = 1;

	@RequestMapping(value = "/showposts", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult showposts(HttpServletRequest request, Integer gender,
			String orderType, int page) throws NeedLoginException {
		ShowPostOrder order = ShowPostOrder.getShowPostOrderByType(orderType);
		if (order == null) {
			return AjaxResult.ERROR_RESULT;
		}
		return showPosts(request, gender, order, page);
	}

	public ListJsonResult showPosts(HttpServletRequest request, Integer gender,
			ShowPostOrder order, int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long cityId = 0L;
		ProfileCache loginUser = getLoginUserCache(request);
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		PagerManager pager = new PagerManager(page, webShowUsersMaxRows,
				postService.countNewOrOnlinePosts(cityId, null, gender,
						loginUser.getUid()));
		List<Post> postList = null;
		if (pager.getTotalResults() > 0) {
			postList = postService.listNewOrOnlinePosts(cityId, null, gender,
					order, loginUser.getUid(), pager.getFirstResult(),
					pager.getMaxResult()).getPosts();
		} else {
			List<Post> recommendPostList = recommendPostService
					.listRecommendPost(webShowUsersMaxRows);
			postList = new ArrayList<Post>();
			if (CollectionUtils.isNotEmpty(recommendPostList)) {
				for (Post post : recommendPostList) {
					if (post.getCreateUid() != loginUser.getUid()) {
						postList.add(post);
					}
				}
			}
		}
		List<UserMView> userViewList = new ArrayList<UserMView>(postList.size());
		for (Post post : postList) {
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(post.getCreateUid());
			UserMView view = userMViewHelper.createUserMView(context,
					profileCache, false);
			view.setPostView(postMViewHelper.createPostMView(context, post));
			userViewList.add(view);
		}
		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, userViewList);
		return result;
	}

	@RequestMapping(value = "/respPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult respPost(HttpServletRequest request, long postId)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			postService.responsePost(context.getUid(), postId,
					StringUtils.EMPTY);
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/sendPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendPost(HttpServletRequest request, Model model,
			PostForm postForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			long postId = postService.createPost(context.getUid(), postForm);
			if (postId > 0 && context.getTpId() > 0) {
				postService.synchronizeWeibo(context.getUid(),
						context.getTpId(), postId);
			}
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
