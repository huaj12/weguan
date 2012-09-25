package com.juzhai.android.dialog.model;

import com.juzhai.android.core.model.Entity;

public class DialogContent extends Entity {
	private static final long serialVersionUID = -3656049529919195124L;

	private long dialogContentId;

	private String content;

	private String imgUrl;

	private long senderUid;

	private long receiverUid;

	private long createTime;

	public long getDialogContentId() {
		return dialogContentId;
	}

	public void setDialogContentId(long dialogContentId) {
		this.dialogContentId = dialogContentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public long getSenderUid() {
		return senderUid;
	}

	public void setSenderUid(long senderUid) {
		this.senderUid = senderUid;
	}

	public long getReceiverUid() {
		return receiverUid;
	}

	public void setReceiverUid(long receiverUid) {
		this.receiverUid = receiverUid;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public Object getIdentify() {
		return this.dialogContentId;
	}

}
