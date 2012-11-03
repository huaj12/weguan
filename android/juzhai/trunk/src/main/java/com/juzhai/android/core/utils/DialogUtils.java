package com.juzhai.android.core.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.dialog.ErrorPromptDialog;
import com.juzhai.android.core.widget.dialog.SuccessPromptDialog;

public class DialogUtils {
	// public static void showAlertDialog(final Context context, int message) {
	// new AlertDialog.Builder(context)
	// .setMessage(message)
	// .setNegativeButton(R.string.close,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int whichButton) {
	// dialog.cancel();
	// }
	// }).show();
	// }

	public static void showSuccessDialog(Context context, int messageId,
			int closeDelay) {
		if (isFinishing(context)) {
			return;
		}
		new SuccessPromptDialog(context, messageId, closeDelay).show();
	}

	public static void showSuccessDialog(Context context, int messageId) {
		if (isFinishing(context)) {
			return;
		}
		new SuccessPromptDialog(context, messageId).show();
	}

	public static void showErrorDialog(Context context, int messageId) {
		if (isFinishing(context)) {
			return;
		}
		new ErrorPromptDialog(context, messageId).show();
	}

	public static void showSuccessDialog(Context context, String message) {
		if (isFinishing(context)) {
			return;
		}
		new SuccessPromptDialog(context, message).show();
	}

	public static void showErrorDialog(Context context, String message) {
		if (isFinishing(context)) {
			return;
		}
		new ErrorPromptDialog(context, message).show();
	}

	// public static void showToastText(Context context, int message) {
	// Toast.makeText(context, context.getResources().getString(message),
	// Toast.LENGTH_LONG).show();
	// }
	//
	// public static void showToastText(Context mContext, String message) {
	// Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
	// }

	public static void showConfirmDialog(Context context,
			final AsyncTask<Void, Void, String> task, String message) {
		new AlertDialog.Builder(context)
				.setMessage(message)
				.setTitle(context.getResources().getString(R.string.confirm))
				.setPositiveButton(
						context.getResources().getString(R.string.ok),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								task.execute();
							}
						})
				.setNegativeButton(
						context.getResources().getString(R.string.cancel),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).show();
	}

	public static void showConfirmDialog(Context context,
			final AsyncTask<Void, Void, String> task, int message) {
		showConfirmDialog(context, task,
				context.getResources().getString(message));
	}

	private static boolean isFinishing(Context context) {
		if (context == null) {
			return true;
		}
		Activity activity = ((Activity) context);
		return activity.isFinishing();
	}
}
