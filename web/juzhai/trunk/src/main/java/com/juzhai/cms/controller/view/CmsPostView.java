package com.juzhai.cms.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Post;

public class CmsPostView {

	private Post post;

	private ProfileCache profileCache;

	public CmsPostView(Post post, ProfileCache profileCache) {
		this.post = post;
		this.profileCache = profileCache;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
