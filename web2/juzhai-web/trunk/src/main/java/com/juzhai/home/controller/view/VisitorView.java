package com.juzhai.home.controller.view;

import java.io.Serializable;
import java.util.Date;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Post;

public class VisitorView implements Serializable {

	private static final long serialVersionUID = -4186692087917259134L;

	private ProfileCache profileCache;

	private Date visitTime;

	private boolean hasInterest;

	private boolean online;

	private Post latestPost;

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
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
