package com.juzhai.index.controller.view;

import com.juzhai.passport.model.Profile;
import com.juzhai.post.model.Post;

public class QueryUserView {

	private Profile profile;

	private boolean online;

	private Post post;

	public QueryUserView(Profile profile, boolean online, Post post) {
		this.profile = profile;
		this.online = online;
		this.post = post;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
