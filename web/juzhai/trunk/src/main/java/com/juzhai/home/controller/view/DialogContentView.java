package com.juzhai.home.controller.view;

import com.juzhai.home.model.DialogContent;
import com.juzhai.passport.bean.ProfileCache;

public class DialogContentView {

	private DialogContent dialogContent;

	/**
	 * 发送者Profile
	 */
	private ProfileCache profile;

	private ProfileCache receiverProfile;

	private boolean shield;

	public boolean isShield() {
		return shield;
	}

	public void setShield(boolean shield) {
		this.shield = shield;
	}

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
