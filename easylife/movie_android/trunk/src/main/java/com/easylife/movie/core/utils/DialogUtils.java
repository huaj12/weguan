package com.easylife.movie.core.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.easylife.movie.core.dialog.ErrorPromptDialog;
import com.easylife.movie.core.dialog.SuccessPromptDialog;

public class DialogUtils {

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

	private static boolean isFinishing(Context context) {
		if (context == null) {
			return true;
		}
		Activity activity = ((Activity) context);
		return activity.isFinishing();
	}

	public static void showDialogList(Context context, int title,
			String[] param, final DialogCallBack callBack) {
		new AlertDialog.Builder(context).setTitle(title)
				.setItems(param, new OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, int which) {
						dialog.cancel();
						callBack.callBack(which);
					}
				}).show();
	}

	public interface DialogCallBack {
		public void callBack(int which);
	}
}
