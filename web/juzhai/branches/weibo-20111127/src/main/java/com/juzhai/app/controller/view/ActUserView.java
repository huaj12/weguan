package com.juzhai.app.controller.view;

import java.io.Serializable;
import java.util.Date;

import com.juzhai.passport.bean.ProfileCache;

public class ActUserView implements Serializable {

	private static final long serialVersionUID = -2918379882074155619L;

	private ProfileCache profileCache;

	private Date createTime;

	private boolean friend;

	public ActUserView(ProfileCache profileCache, Date createTime,
			boolean friend) {
		super();
		this.profileCache = profileCache;
		this.createTime = createTime;
		this.friend = friend;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isFriend() {
		return friend;
	}

	public void setFriend(boolean friend) {
		this.friend = friend;
	}

}
