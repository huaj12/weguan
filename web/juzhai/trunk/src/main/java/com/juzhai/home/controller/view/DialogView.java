package com.juzhai.home.controller.view;

import com.juzhai.home.model.Dialog;
import com.juzhai.home.model.DialogContent;
import com.juzhai.passport.bean.ProfileCache;

public class DialogView {

	private Dialog dialog;

	private DialogContent dialogContent;

	private long dialogContentCnt;

	private ProfileCache targetProfile;

	private ProfileCache userProfile;

	private boolean shield;

	public boolean isShield() {
		return shield;
	}

	public void setShield(boolean shield) {
		this.shield = shield;
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	public DialogContent getDialogContent() {
		return dialogContent;
	}

	public void setDialogContent(DialogContent dialogContent) {
		this.dialogContent = dialogContent;
	}

	public long getDialogContentCnt() {
		return dialogContentCnt;
	}

	public void setDialogContentCnt(long dialogContentCnt) {
		this.dialogContentCnt = dialogContentCnt;
	}

	public ProfileCache getTargetProfile() {
		return targetProfile;
	}

	public void setTargetProfile(ProfileCache targetProfile) {
		this.targetProfile = targetProfile;
	}

	public ProfileCache getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(ProfileCache userProfile) {
		this.userProfile = userProfile;
	}

}
