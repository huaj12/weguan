package com.juzhai.post.controller.view;

import java.util.Date;

import com.juzhai.passport.bean.ProfileCache;

public class ResponseUserView {

	private ProfileCache profileCache;

	private boolean hasInterest;

	private Date createTime;

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
