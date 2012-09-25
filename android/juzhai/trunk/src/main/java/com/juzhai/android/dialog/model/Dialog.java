package com.juzhai.android.dialog.model;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.passport.model.User;

public class Dialog extends Entity {

	private static final long serialVersionUID = -8137925571716013807L;

	private long dialogId;

	private long receiverUid;

	private String latestContent;

	private long createTime;

	private long dialogContentCount;

	private User targetUser;

	public long getDialogId() {
		return dialogId;
	}

	public void setDialogId(long dialogId) {
		this.dialogId = dialogId;
	}

	public long getReceiverUid() {
		return receiverUid;
	}

	public void setReceiverUid(long receiverUid) {
		this.receiverUid = receiverUid;
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

	public long getDialogContentCount() {
		return dialogContentCount;
	}

	public void setDialogContentCount(long dialogContentCount) {
		this.dialogContentCount = dialogContentCount;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public Object getIdentify() {
		return this.getDialogId();
	}

}
