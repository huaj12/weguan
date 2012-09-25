package com.juzhai.android.dialog.exception;

public class DialogContentException extends Exception {

	private static final long serialVersionUID = 1579295456821382028L;
	private int messageId;

	public DialogContentException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public DialogContentException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public DialogContentException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public DialogContentException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
