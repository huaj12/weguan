package com.juzhai.android.passport.exception;

public class PassportException extends Exception {

	private static final long serialVersionUID = -950569690585929599L;

	private int messageId;

	public PassportException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public PassportException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public PassportException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public PassportException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
