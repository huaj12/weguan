package com.juzhai.index.controller.view;

import java.util.Date;
import java.util.List;

import com.juzhai.act.controller.view.UserActView;
import com.juzhai.passport.model.Profile;

public class ShowUserView {

	private Profile profile;

	private List<UserActView> userActViewList;

	private boolean hasInterest;

	private boolean hasDating;

	private boolean online;

	private List<Date> freeDateList;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public boolean isHasDating() {
		return hasDating;
	}

	public void setHasDating(boolean hasDating) {
		this.hasDating = hasDating;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public List<UserActView> getUserActViewList() {
		return userActViewList;
	}

	public void setUserActViewList(List<UserActView> userActViewList) {
		this.userActViewList = userActViewList;
	}

	public List<Date> getFreeDateList() {
		return freeDateList;
	}

	public void setFreeDateList(List<Date> freeDateList) {
		this.freeDateList = freeDateList;
	}
}
