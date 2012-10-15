package com.juzhai.android.post.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class PostException extends JuzhaiException {

	private static final long serialVersionUID = 1191329540989752039L;

	public PostException(Context context, int messageId) {
		super(context, messageId);
	}

	public PostException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public PostException(Context context, int messageId, Throwable throwable) {
		super(context, messageId, throwable);
	}

	public PostException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
