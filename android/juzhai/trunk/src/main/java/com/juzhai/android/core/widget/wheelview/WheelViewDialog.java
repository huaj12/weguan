package com.juzhai.android.core.widget.wheelview;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.juzhai.android.R;
import com.juzhai.android.common.service.CommonData;
import com.juzhai.android.core.model.Entity;

public class WheelViewDialog<T extends Entity> extends AlertDialog {
	private int title;
	private WheelViewCallBack callback;
	private Context context;
	private View view;
	private WheelView wheelView;

	public WheelViewDialog(int title, T selectedEntity, List<T> datas,
			Context context, WheelViewCallBack callback) {
		super(context);
		this.title = title;
		this.callback = callback;
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.fragment_wheelview, null);
		wheelView = (WheelView) view.findViewById(R.id.wheelview);
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
	}

	// TODO (done) 不是这么个意思，不是让你方法换一个地方⋯⋯。面向对象！

	@Override
	public void show() {
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
