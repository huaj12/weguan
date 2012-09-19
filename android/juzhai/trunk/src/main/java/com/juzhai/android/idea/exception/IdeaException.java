package com.juzhai.android.idea.exception;

public class IdeaException extends Exception {
	private static final long serialVersionUID = 7400271977091952584L;
	private int messageId;

	public IdeaException(int messageId) {
		super();
		this.messageId = messageId;
	}

	public IdeaException(int messageId, Throwable throwable) {
		super(throwable);
		this.messageId = messageId;
	}

	public IdeaException(String detailMessage, int messageId) {
		super(detailMessage);
		this.messageId = messageId;
	}

	public IdeaException(String detailMessage, int messageId,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}
}
