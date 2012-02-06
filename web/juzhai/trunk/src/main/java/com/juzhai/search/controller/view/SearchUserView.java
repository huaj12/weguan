package com.juzhai.search.controller.view;

import com.juzhai.passport.model.Profile;
import com.juzhai.post.model.Post;

public class SearchUserView {

	private Profile profile;

	private boolean isOnline;

	private Post post;

	public SearchUserView(Profile profile, boolean isOnline, Post post) {
		this.profile=profile;
		this.isOnline=isOnline;
		this.post=post;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
