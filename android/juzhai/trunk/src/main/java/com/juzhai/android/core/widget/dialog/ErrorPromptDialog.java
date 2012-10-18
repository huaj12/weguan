package com.juzhai.android.core.widget.dialog;

import android.content.Context;

import com.juzhai.android.R;

public class ErrorPromptDialog extends PromptDialog {

	public ErrorPromptDialog(Context context, int message) {
		super(context, message, R.drawable.cross);
	}

}
