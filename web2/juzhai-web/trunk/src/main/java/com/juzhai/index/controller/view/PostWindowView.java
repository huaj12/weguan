package com.juzhai.index.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.PostWindow;

public class PostWindowView {

	private PostWindow postWindow;

	private ProfileCache profileCache;

	public PostWindow getPostWindow() {
		return postWindow;
	}

	public void setPostWindow(PostWindow postWindow) {
		this.postWindow = postWindow;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

}
