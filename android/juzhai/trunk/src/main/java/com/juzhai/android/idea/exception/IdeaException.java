package com.juzhai.android.idea.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class IdeaException extends JuzhaiException {

	private static final long serialVersionUID = 7400271977091952584L;

	public IdeaException(Context context, int messageId) {
		super(context, messageId);
	}

	public IdeaException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public IdeaException(Context context, int messageId, Throwable throwable) {
		super(context, messageId, throwable);
	}

	public IdeaException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
