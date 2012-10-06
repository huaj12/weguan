package com.juzhai.android.core.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
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
		Toast.makeText(context, context.getResources().getString(message),
				Toast.LENGTH_LONG).show();
	}

	public static void showToastText(Context mContext, String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
	}

	public static void showConfirmDialog(Context context,
			final AsyncTask<Void, Void, String> task) {
		new AlertDialog.Builder(context)
				.setMessage(
						context.getResources().getString(
								R.string.confirm_message))
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
		;

	}
}
