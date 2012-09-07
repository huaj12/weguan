package com.juzhai.home.controller.view;

import com.juzhai.passport.model.Profile;

public class RecommendUserView {

	private Profile profile;

	private boolean hasInterest;

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
}
