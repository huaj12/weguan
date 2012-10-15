package com.juzhai.android.passport.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class PassportException extends JuzhaiException {

	private static final long serialVersionUID = -950569690585929599L;

	public PassportException(Context context, int messageId) {
		super(context, messageId);
	}

	public PassportException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public PassportException(Context context, int messageId, Throwable throwable) {
		super(context, messageId, throwable);
	}

	public PassportException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
