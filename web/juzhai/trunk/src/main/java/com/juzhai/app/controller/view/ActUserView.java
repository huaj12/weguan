package com.juzhai.app.controller.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.juzhai.passport.bean.ProfileCache;

public class ActUserView implements Serializable {

	private static final long serialVersionUID = -2918379882074155619L;

	private ProfileCache profileCache;

	private Date createTime;

	private boolean friend;

	private boolean online;

	private Boolean hasInterest;

	private Boolean hasDating;

	private List<Date> freeDateList;

	public ActUserView(ProfileCache profileCache, Date createTime,
			boolean friend) {
		super();
		this.profileCache = profileCache;
		this.createTime = createTime;
		this.friend = friend;
	}

	public ActUserView(ProfileCache profileCache, Date createTime,
			Boolean hasInterest, Boolean hasDating, boolean online,
			List<Date> freeDateList) {
		super();
		this.profileCache = profileCache;
		this.createTime = createTime;
		this.hasInterest = hasInterest;
		this.hasDating = hasDating;
		this.online = online;
		this.freeDateList = freeDateList;
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

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Boolean getHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(Boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public Boolean getHasDating() {
		return hasDating;
	}

	public void setHasDating(Boolean hasDating) {
		this.hasDating = hasDating;
	}

	public List<Date> getFreeDateList() {
		return freeDateList;
	}

	public void setFreeDateList(List<Date> freeDateList) {
		this.freeDateList = freeDateList;
	}
}
