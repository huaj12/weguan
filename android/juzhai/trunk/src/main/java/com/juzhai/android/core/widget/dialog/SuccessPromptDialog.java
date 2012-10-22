package com.juzhai.android.core.widget.dialog;

import android.content.Context;

import com.juzhai.android.R;

public class SuccessPromptDialog extends PromptDialog {

	public SuccessPromptDialog(Context context, int message) {
		super(context, message, R.drawable.tick);
	}

	public SuccessPromptDialog(Context context, String message) {
		super(context, message, R.drawable.tick);
	}

	public SuccessPromptDialog(Context context, String message, int closetime,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.tick, closetime, callback);
	}

	public SuccessPromptDialog(Context context, int message, int closetime,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.tick, closetime, callback);
	}

	public SuccessPromptDialog(Context context, String message, int closetime) {
		super(context, message, R.drawable.tick, closetime, null);
	}

	public SuccessPromptDialog(Context context, int message, int closetime) {
		super(context, message, R.drawable.tick, closetime, null);
	}

	public SuccessPromptDialog(Context context, String message,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.tick, callback);
	}

	public SuccessPromptDialog(Context context, int message,
			PromptDialogCallback callback) {
		super(context, message, R.drawable.tick, callback);
	}

}
