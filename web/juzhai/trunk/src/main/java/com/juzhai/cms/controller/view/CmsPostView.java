package com.juzhai.cms.controller.view;

import com.juzhai.post.model.Post;

public class CmsPostView {

	private Post post;

	private String username;

	public CmsPostView(Post post, String username) {
		this.post = post;
		this.username = username;
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
