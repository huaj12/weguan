package com.juzhai.android.dialog.exception;

public class DialogException extends Exception {

	private static final long serialVersionUID = 366266015729859657L;
	private int messageId;

	public DialogException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public DialogException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public DialogException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public DialogException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
