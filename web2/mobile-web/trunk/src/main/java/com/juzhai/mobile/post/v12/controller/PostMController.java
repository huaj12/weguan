package com.juzhai.mobile.post.v12.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.passport.v12.controller.view.UserMView;
import com.juzhai.mobile.passport.v12.controller.viewHelper.IUserMViewHelper;
import com.juzhai.mobile.post.v12.controller.viewHelper.IPostMViewHelper;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileRemoteService;
import com.juzhai.post.bean.ShowPostOrder;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostRemoteService;
import com.juzhai.post.service.IRecommendPostRemoteService;

@Controller("v12PostMController")
@RequestMapping("post")
public class PostMController extends BaseController {

	@Autowired
	private IPostRemoteService postService;
	@Autowired
	private IRecommendPostRemoteService recommendPostService;
	@Autowired
	private IProfileRemoteService profileService;
	@Autowired
	private IUserMViewHelper v12UserMViewHelper;
	@Autowired
	private IPostMViewHelper v12PostMViewHelper;
	@Value("${mobile.show.users.max.rows}")
	private int mobileShowUsersMaxRows = 1;

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

	private ListJsonResult showPosts(HttpServletRequest request,
			Integer gender, ShowPostOrder order, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		long cityId = 0L;
		ProfileCache loginUser = profileService.getProfileCacheByUid(context
				.getUid());
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		PagerManager pager = new PagerManager(page, mobileShowUsersMaxRows,
				postService.countNewOrOnlinePosts(cityId, null, gender,
						loginUser.getUid()));
		List<Post> postList = null;
		if (pager.getTotalResults() > 0) {
			postList = postService.listNewOrOnlinePosts(cityId, null, gender,
					order, loginUser.getUid(), pager.getFirstResult(),
					pager.getMaxResult()).getPosts();
		} else {
			List<Post> recommendPostList = recommendPostService
					.listRecommendPost(mobileShowUsersMaxRows);
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
			UserMView view = v12UserMViewHelper.createUserMView(context,
					profileCache, false);
			view.setPostView(v12PostMViewHelper.createPostMView(context, post));
			userViewList.add(view);
		}
		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, userViewList);
		return result;
	}
}
