package com.juzhai.post.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Post;

public class PostView {

	private ProfileCache profileCache;

	private Post post;

	private boolean hasResponse;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(boolean hasResponse) {
		this.hasResponse = hasResponse;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}
}
