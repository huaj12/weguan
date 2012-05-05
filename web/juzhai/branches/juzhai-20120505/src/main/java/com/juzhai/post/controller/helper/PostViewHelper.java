package com.juzhai.post.controller.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;

@Component
public class PostViewHelper {
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IInterestUserService interestUserService;

	public List<PostView> assembleUserPostViewList(UserContext context,
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
}
