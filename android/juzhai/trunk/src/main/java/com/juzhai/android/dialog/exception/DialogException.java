package com.juzhai.android.dialog.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class DialogException extends JuzhaiException {

	private static final long serialVersionUID = 366266015729859657L;

	public DialogException(Context context, int messageId) {
		super(context, messageId);
	}

	public DialogException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public DialogException(Context context, int messageId, Throwable throwable) {
		super(context, messageId, throwable);
	}

	public DialogException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
