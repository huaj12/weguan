package com.juzhai.android.core.widget.dialog;

import android.content.Context;

import com.juzhai.android.R;

public class ErrorPromptDialog extends PromptDialog {

	public ErrorPromptDialog(Context context, int message) {
		super(context, message, R.drawable.cross);
	}

	public ErrorPromptDialog(Context context, String message) {
		super(context, message, R.drawable.cross);
	}

	public ErrorPromptDialog(Context context, String message, int closetime,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.cross, closetime, callback);
	}

	public ErrorPromptDialog(Context context, int message, int closetime,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.cross, closetime, callback);
	}

	public ErrorPromptDialog(Context context, String message, int closetime) {
		super(context, message, R.drawable.cross, closetime, null);
	}

	public ErrorPromptDialog(Context context, int message, int closetime) {
		super(context, message, R.drawable.cross, closetime, null);
	}

	public ErrorPromptDialog(Context context, String message,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.cross, callback);
	}

	public ErrorPromptDialog(Context context, int message,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.cross, callback);
	}

}
