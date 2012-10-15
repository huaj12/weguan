package com.juzhai.android.core.exception;

import android.content.Context;

public class JuzhaiException extends Exception {

	private static final long serialVersionUID = -950569690585929599L;

	private int messageId = -1;

	private Context context;

	public JuzhaiException(Context context, int messageId) {
		super();
		this.context = context;
		this.messageId = messageId;
	}

	public JuzhaiException(Context context, String detailMessage) {
		super(detailMessage);
		this.context = context;
	}

	public JuzhaiException(Context context, int messageId, Throwable throwable) {
		super(throwable);
		this.context = context;
		this.messageId = messageId;
	}

	public JuzhaiException(Context context, String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		this.context = context;
	}

	@Override
	public String getMessage() {
		String message = null;
		if (this.messageId >= 0) {
			message = context.getString(this.messageId);
		} else {
			message = super.getMessage();
		}
		return message;
	}
}
