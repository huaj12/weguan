package com.juzhai.android.home.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class HomeException extends JuzhaiException {

	private static final long serialVersionUID = 8856194263333524200L;

	public HomeException(Context context, int messageId) {
		super(context, messageId);
	}

	public HomeException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public HomeException(Context context, int messageId, Throwable throwable) {
		super(context, messageId, throwable);
	}

	public HomeException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
