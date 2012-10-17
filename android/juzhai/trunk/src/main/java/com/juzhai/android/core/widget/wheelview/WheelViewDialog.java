package com.juzhai.android.core.widget.wheelview;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import com.juzhai.android.R;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.model.Entity;

public class WheelViewDialog {
	// TODO (review) 不是这么个意思，不是让你方法换一个地方⋯⋯。面向对象！
	public static <T extends Entity> void showWheelView(int title,
			T selectedEntity, final List<T> datas, Context context,
			final WheelViewCallBack callback) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_wheelview, null);
		final WheelView wheelView = (WheelView) view
				.findViewById(R.id.wheelview);
		wheelView.TEXT_SIZE = 35;
		ArrayWheelAdapter<T> wheelViewAdapter = new ArrayWheelAdapter<T>(datas,
				20);
		wheelView.setAdapter(wheelViewAdapter);
		if (selectedEntity != null) {
			wheelView.setCurrentItem(CommonData.getDataIndxex(
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

	public interface WheelViewCallBack {
		void callback(int index);
	}
}
