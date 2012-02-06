package com.juzhai.cms.controller.view;

import com.juzhai.post.model.Post;

public class CmsPostView {
	// TODO (review) 不要中文。我到时候jstl会做插件
	private String[] purposes = { "我想去", "我想找伴去", "我想和一个男生", "我想和一个" };
	private Post post;

	private String username;

	// TODO (review) 不用在java里组装。我到时候jstl会做插件
	private String purposeName;

	public CmsPostView(Post post, String username, int purpose) {
		this.post = post;
		this.username = username;
		this.purposeName = purposes[purpose];
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

	public String getPurposeName() {
		return purposeName;
	}

	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

}
