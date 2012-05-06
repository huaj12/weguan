package com.juzhai.home.controller.view;

import java.io.Serializable;
import java.util.Date;

import com.juzhai.passport.bean.ProfileCache;

public class VisitorView implements Serializable {

	private static final long serialVersionUID = -4186692087917259134L;

	private ProfileCache profileCache;

	private Date visitTime;

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
}
