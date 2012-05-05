package com.juzhai.home.bean;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.Dating;
import com.juzhai.passport.bean.ProfileCache;

public class DatingView {

	private Dating dating;

	private Act act;

	private ProfileCache profileCache;

	public DatingView(Dating dating, Act act, ProfileCache profileCache) {
		super();
		this.dating = dating;
		this.act = act;
		this.profileCache = profileCache;
	}

	public Dating getDating() {
		return dating;
	}

	public void setDating(Dating dating) {
		this.dating = dating;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}
}
