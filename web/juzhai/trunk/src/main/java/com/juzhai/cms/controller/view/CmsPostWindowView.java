package com.juzhai.cms.controller.view;

import com.juzhai.post.model.PostWindow;

public class CmsPostWindowView {
	private PostWindow postWindow;
	private String userLogo;

	public CmsPostWindowView(PostWindow postWindow, String userLogo) {
		this.postWindow = postWindow;
		this.userLogo = userLogo;
	}

	public PostWindow getPostWindow() {
		return postWindow;
	}

	public void setPostWindow(PostWindow postWindow) {
		this.postWindow = postWindow;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

}
