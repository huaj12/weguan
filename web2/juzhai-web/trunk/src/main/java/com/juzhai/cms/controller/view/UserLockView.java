package com.juzhai.cms.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Passport;

public class UserLockView {
	private Passport passport;
	private ProfileCache profile;

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public ProfileCache getProfile() {
		return profile;
	}

	public void setProfile(ProfileCache profile) {
		this.profile = profile;
	}

}
