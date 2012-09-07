package com.juzhai.home.controller.view;

import java.io.Serializable;

import com.juzhai.home.model.DialogContent;
import com.juzhai.passport.bean.ProfileCache;

public class DialogContentView implements Serializable {

	private static final long serialVersionUID = -3865998527250284427L;

	private DialogContent dialogContent;
	/**
	 * 发送者Profile
	 */
	private ProfileCache profile;

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
