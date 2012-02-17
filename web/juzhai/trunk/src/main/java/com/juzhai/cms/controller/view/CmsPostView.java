package com.juzhai.cms.controller.view;

import com.juzhai.post.model.Post;

public class CmsPostView {

	private Post post;

	private String username;
	
	private String userLogo;
	
	

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public CmsPostView(Post post, String username,String userLogo) {
		this.post = post;
		this.username = username;
		this.userLogo=userLogo;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
