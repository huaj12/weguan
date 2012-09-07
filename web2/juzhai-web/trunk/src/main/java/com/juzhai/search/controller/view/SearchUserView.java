package com.juzhai.search.controller.view;

import java.util.Date;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Post;

public class SearchUserView {

	private ProfileCache profile;

	private boolean online;

	private Post post;

	private boolean hasInterest;

	private Date lastWebLoginTime;

	public ProfileCache getProfile() {
		return profile;
	}

	public void setProfile(ProfileCache profile) {
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

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public Date getLastWebLoginTime() {
		return lastWebLoginTime;
	}

	public void setLastWebLoginTime(Date lastWebLoginTime) {
		this.lastWebLoginTime = lastWebLoginTime;
	}

}
