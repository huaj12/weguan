package com.juzhai.android.core.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.juzhai.android.R;

public class DialogUtils {
	public static void showAlertDialog(final Context mContext, int message) {
		new AlertDialog.Builder(mContext)
				.setMessage(message)
				.setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Activity activity = ((Activity) mContext);
								activity.setResult(activity.RESULT_OK);
							}
						}).show();
	}

	public static void showToastText(Context mContext, int message) {
		Toast.makeText(mContext, mContext.getResources().getString(message),
				5000).show();
	}

	public static void showToastText(Context mContext, String message) {
		Toast.makeText(mContext, message, 5000).show();
	}
}
