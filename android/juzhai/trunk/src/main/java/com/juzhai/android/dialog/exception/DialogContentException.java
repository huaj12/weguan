package com.juzhai.android.dialog.exception;

import android.content.Context;

import com.juzhai.android.core.exception.JuzhaiException;

public class DialogContentException extends JuzhaiException {

	private static final long serialVersionUID = 1579295456821382028L;

	public DialogContentException(Context context, int messageId) {
		super(context, messageId);
	}

	public DialogContentException(Context context, String detailMessage) {
		super(context, detailMessage);
	}

	public DialogContentException(Context context, int messageId,
			Throwable throwable) {
		super(context, messageId, throwable);
	}

	public DialogContentException(Context context, String detailMessage,
			Throwable throwable) {
		super(context, detailMessage, throwable);
	}
}
