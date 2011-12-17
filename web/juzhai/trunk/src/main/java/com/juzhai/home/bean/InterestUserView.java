package com.juzhai.home.bean;

import java.util.List;

import com.juzhai.act.controller.view.UserActView;
import com.juzhai.passport.bean.ProfileCache;

public class InterestUserView {

	private ProfileCache profileCache;

	private List<UserActView> userActViewList;

	private DatingView datingView;

	private boolean hasInterest;

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public List<UserActView> getUserActViewList() {
		return userActViewList;
	}

	public void setUserActViewList(List<UserActView> userActViewList) {
		this.userActViewList = userActViewList;
	}

	public DatingView getDatingView() {
		return datingView;
	}

	public void setDatingView(DatingView datingView) {
		this.datingView = datingView;
	}

}
