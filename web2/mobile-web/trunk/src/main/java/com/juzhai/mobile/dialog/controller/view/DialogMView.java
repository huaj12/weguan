package com.juzhai.mobile.dialog.controller.view;

import com.juzhai.mobile.passport.controller.view.UserMView;

public class DialogMView {

	private long dialogId;

	private long receiverUid;

	private String latestContent;

	private long createTime;

	private long dialogContentCount;

	private UserMView targetUser;

	public long getDialogId() {
		return dialogId;
	}

	public void setDialogId(long dialogId) {
		this.dialogId = dialogId;
	}

	public String getLatestContent() {
		return latestContent;
	}

	public void setLatestContent(String latestContent) {
		this.latestContent = latestContent;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public UserMView getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(UserMView targetUser) {
		this.targetUser = targetUser;
	}

	public long getDialogContentCount() {
		return dialogContentCount;
	}

	public void setDialogContentCount(long dialogContentCount) {
		this.dialogContentCount = dialogContentCount;
	}

	public long getReceiverUid() {
		return receiverUid;
	}

	public void setReceiverUid(long receiverUid) {
		this.receiverUid = receiverUid;
	}

}
