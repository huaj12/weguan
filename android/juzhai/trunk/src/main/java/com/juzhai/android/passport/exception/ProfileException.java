package com.juzhai.android.passport.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class ProfileException extends JuzhaiException {

	private static final long serialVersionUID = 1866314733010976442L;

	public ProfileException(Context context, int messageId) {
		super(context, messageId);
	}

	public ProfileException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public ProfileException(Context context, int messageId, Throwable throwable) {
		super(context, messageId, throwable);
	}

	public ProfileException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
