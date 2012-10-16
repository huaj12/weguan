package com.juzhai.android.core.utils;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.widget.wheelview.ArrayWheelAdapter;
import com.juzhai.android.core.widget.wheelview.WheelView;

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

	// TODO (review) WheelView.WheelViewCallBack 多此一举
	public static <T extends Entity> void showWheelView(int title,
			T selectedEntity, final List<T> datas, Context context,
			final WheelView.WheelViewCallBack callback) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_wheelview, null);
		final WheelView wheelView = (WheelView) view
				.findViewById(R.id.wheelview);
		wheelView.TEXT_SIZE = 35;
		// TODO (done) 20表达什么？另外，是px还是dp，还是sp 文字的最大宽度
		ArrayWheelAdapter<T> wheelViewAdapter = new ArrayWheelAdapter<T>(datas,
				20);
		wheelView.setAdapter(wheelViewAdapter);
		if (selectedEntity != null) {
			wheelView.setCurrentItem(JzUtils.getDataIndxex(
					(Long) selectedEntity.getIdentify(), datas));
		} else {
			wheelView.setCurrentItem(0);
		}
		new AlertDialog.Builder(context)
				.setTitle(context.getResources().getString(title))
				.setView(view)
				.setPositiveButton(R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.callback(wheelView.getCurrentItem());
					}
				}).setNegativeButton(R.string.cancel, null).show();

	}

}
