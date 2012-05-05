package com.juzhai.home.bean;

import java.util.Date;
import java.util.List;

import com.juzhai.act.controller.view.UserActView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Post;

public class InterestUserView {

	private ProfileCache profileCache;

	private List<UserActView> userActViewList;

	private boolean hasDating;

	private boolean hasInterest;

	private boolean online;

	private List<Date> freeDateList;

	private Post latestPost;

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public List<UserActView> getUserActViewList() {
		return userActViewList;
	}

	public void setUserActViewList(List<UserActView> userActViewList) {
		this.userActViewList = userActViewList;
	}

	public boolean isHasDating() {
		return hasDating;
	}

	public void setHasDating(boolean hasDating) {
		this.hasDating = hasDating;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public List<Date> getFreeDateList() {
		return freeDateList;
	}

	public void setFreeDateList(List<Date> freeDateList) {
		this.freeDateList = freeDateList;
	}

	public Post getLatestPost() {
		return latestPost;
	}

	public void setLatestPost(Post latestPost) {
		this.latestPost = latestPost;
	}
}
