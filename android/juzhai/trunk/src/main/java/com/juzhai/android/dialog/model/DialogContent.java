package com.juzhai.android.dialog.model;

import android.graphics.Bitmap;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.dialog.bean.MessageStatus;

public class DialogContent extends Entity {

	private static final long serialVersionUID = -3656049529919195124L;

	private Long dialogContentId;

	private String content;

	private String imgUrl;

	private long senderUid;

	private long receiverUid;

	private long createTime;

	private MessageStatus status;

	private Bitmap image;

	public Long getDialogContentId() {
		return dialogContentId;
	}

	public void setDialogContentId(Long dialogContentId) {
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

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

}
