package com.juzhai.search.controller.view;

import java.util.Date;

import com.juzhai.passport.bean.ProfileCache;

public class LuceneUserView {
	private ProfileCache profileCache;

	private Date lastWebLoginTime;

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public Date getLastWebLoginTime() {
		return lastWebLoginTime;
	}

	public void setLastWebLoginTime(Date lastWebLoginTime) {
		this.lastWebLoginTime = lastWebLoginTime;
	}

}
