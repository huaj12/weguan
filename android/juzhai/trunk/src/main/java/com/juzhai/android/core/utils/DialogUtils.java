package com.juzhai.android.core.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.juzhai.android.R;

public class DialogUtils {
	public static void showAlertDialog(final Context context, int message) {
		new AlertDialog.Builder(context)
				.setMessage(message)
				.setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						}).show();
	}

	public static void showToastText(Context context, int message) {
		// TODO (review) 警告处理掉
		Toast.makeText(context, context.getResources().getString(message), 5000)
				.show();
	}

	public static void showToastText(Context mContext, String message) {
		Toast.makeText(mContext, message, 5000).show();
	}
}
