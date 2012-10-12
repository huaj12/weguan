package com.juzhai.android.passport.exception;

public class ProfileException extends Exception {

	private static final long serialVersionUID = 1866314733010976442L;
	private int messageId;

	public ProfileException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public ProfileException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public ProfileException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public ProfileException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
