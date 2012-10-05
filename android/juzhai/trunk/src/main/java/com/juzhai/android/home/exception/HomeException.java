package com.juzhai.android.home.exception;

public class HomeException extends Exception {
	private static final long serialVersionUID = 8856194263333524200L;
	private int messageId;

	public HomeException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public HomeException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public HomeException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public HomeException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
