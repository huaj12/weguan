package com.juzhai.msg.controller.view;

import java.io.Serializable;

import com.juzhai.act.model.Act;
import com.juzhai.passport.bean.ProfileCache;

public class ActMsgView implements Serializable {

	private static final long serialVersionUID = 7087543258625496760L;

	private Act act;

	private ProfileCache profileCache;

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
