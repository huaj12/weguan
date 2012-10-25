package com.juzhai.mobile.dialog.controller.view;

import com.juzhai.core.image.DialogSizeType;
import com.juzhai.core.image.ImageUrl;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.model.DialogContent;

public class DialogContentMView {

	private long dialogContentId;

	private String content;

	private String imgUrl;

	private String bigImgUrl;

	private String originalImgUrl;

	private long senderUid;

	private long receiverUid;

	private long createTime;

	public static DialogContentMView converFromDialogContentView(
			DialogContentView view) {
		return converFromDialogContent(view.getDialogContent());
	}

	public static DialogContentMView converFromDialogContent(
			DialogContent dialogContent) {
		DialogContentMView mView = new DialogContentMView();
		mView.setDialogContentId(dialogContent.getId());
		mView.setContent(dialogContent.getContent());
		mView.setSenderUid(dialogContent.getSenderUid());
		mView.setReceiverUid(dialogContent.getReceiverUid());
		mView.setCreateTime(dialogContent.getCreateTime().getTime());
		mView.setImgUrl(ImageUrl.dialogContentPic(dialogContent.getId(),
				dialogContent.getPic(), DialogSizeType.MIDDLE.getType()));
		mView.setBigImgUrl(ImageUrl.dialogContentPic(dialogContent.getId(),
				dialogContent.getPic(), DialogSizeType.BIG.getType()));
		mView.setOriginalImgUrl(ImageUrl.dialogContentPic(
				dialogContent.getId(), dialogContent.getPic(),
				DialogSizeType.ORIGINAL.getType()));
		return mView;
	}

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getBigImgUrl() {
		return bigImgUrl;
	}

	public void setBigImgUrl(String bigImgUrl) {
		this.bigImgUrl = bigImgUrl;
	}

	public String getOriginalImgUrl() {
		return originalImgUrl;
	}

	public void setOriginalImgUrl(String originalImgUrl) {
		this.originalImgUrl = originalImgUrl;
	}
}
