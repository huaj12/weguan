package com.juzhai.home.controller.view;

import com.juzhai.home.model.DialogContent;
import com.juzhai.passport.bean.ProfileCache;

public class DialogContentView {

	private DialogContent dialogContent;

	/**
	 * 发送者Profile
	 */
	private ProfileCache profile;

	// TODO (done) 换名字，叫receiverProfile
	private ProfileCache receiverProfile;

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

	public ProfileCache getReceiverProfile() {
		return receiverProfile;
	}

	public void setReceiverProfile(ProfileCache receiverProfile) {
		this.receiverProfile = receiverProfile;
	}

}
