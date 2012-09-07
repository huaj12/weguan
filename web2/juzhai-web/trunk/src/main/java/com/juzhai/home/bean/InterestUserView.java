package com.juzhai.home.bean;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Post;

public class InterestUserView {

	private ProfileCache profileCache;

	private boolean hasInterest;

	private boolean online;

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

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Post getLatestPost() {
		return latestPost;
	}

	public void setLatestPost(Post latestPost) {
		this.latestPost = latestPost;
	}
}
