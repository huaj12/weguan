package com.juzhai.act.controller.view;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.passport.bean.ProfileCache;

public class UserActView {

	private UserAct userAct;
	private Act act;
	private ProfileCache profileCache;

	public UserActView(UserAct userAct, Act act, ProfileCache profileCache) {
		super();
		this.userAct = userAct;
		this.act = act;
		this.profileCache = profileCache;
	}

	public UserAct getUserAct() {
		return userAct;
	}

	public void setUserAct(UserAct userAct) {
		this.userAct = userAct;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}
}
