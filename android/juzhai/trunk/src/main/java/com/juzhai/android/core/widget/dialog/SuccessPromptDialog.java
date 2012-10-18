package com.juzhai.android.core.widget.dialog;

import android.content.Context;

import com.juzhai.android.R;

public class SuccessPromptDialog extends PromptDialog {

	public SuccessPromptDialog(Context context, int message) {
		super(context, message, R.drawable.tick);
	}

}
