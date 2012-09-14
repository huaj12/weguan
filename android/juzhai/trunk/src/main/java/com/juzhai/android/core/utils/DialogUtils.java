package com.juzhai.android.core.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.juzhai.android.R;

public class DialogUtils {
	public static void showAlertDialog(final Context mContext, int message) {
		new AlertDialog.Builder(mContext)
				.setMessage(message)
				.setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								((Activity) mContext).finish();
							}
						}).show();
	}
}
