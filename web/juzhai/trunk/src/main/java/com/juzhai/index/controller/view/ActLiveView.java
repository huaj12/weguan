package com.juzhai.index.controller.view;

import java.io.Serializable;
import java.util.Date;

import com.juzhai.act.model.Act;
import com.juzhai.passport.bean.ProfileCache;

public class ActLiveView implements Serializable {

	private static final long serialVersionUID = 8846249314667300073L;

	private ProfileCache profileCache;
	private Act act;
	private Date time;

	public ActLiveView(ProfileCache profileCache, Act act, Date time) {
		super();
		this.profileCache = profileCache;
		this.act = act;
		this.time = time;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
