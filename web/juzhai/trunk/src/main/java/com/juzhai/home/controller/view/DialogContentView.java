package com.juzhai.home.controller.view;

import com.juzhai.home.model.DialogContent;
import com.juzhai.passport.bean.ProfileCache;

public class DialogContentView {

	private DialogContent dialogContent;

	private ProfileCache profile;

	private ProfileCache myProfile;

	public DialogContent getDialogContent() {
		return dialogContent;
	}

	public void setDialogContent(DialogContent dialogContent) {
		this.dialogContent = dialogContent;
	}

	public ProfileCache getProfile() {
		return profile;
	}

	public void setProfile(ProfileCache profile) {
		this.profile = profile;
	}

	public ProfileCache getMyProfile() {
		return myProfile;
	}

	public void setMyProfile(ProfileCache myProfile) {
		this.myProfile = myProfile;
	}

}
