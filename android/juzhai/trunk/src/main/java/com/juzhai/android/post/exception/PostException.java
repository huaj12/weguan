package com.juzhai.android.post.exception;

public class PostException extends Exception {

	private static final long serialVersionUID = 1191329540989752039L;

	private int messageId;

	public PostException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public PostException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public PostException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public PostException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
