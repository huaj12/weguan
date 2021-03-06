package com.juzhai.android.core.widget.wheelview;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Entity;

public class WheelViewDialog<T extends Entity> extends AlertDialog implements
		OnClickListener {
	private WheelViewDialogListener wheelViewDialogListener;
	private WheelView wheelView;

	// TODO (done) selectedEntity应该用选择第几个,或者用identify
	public WheelViewDialog(Context context, int title, int itemIndex,
			List<T> datas, WheelViewDialogListener wheelViewDialogListener) {
		super(context);
		this.wheelViewDialogListener = wheelViewDialogListener;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.fragment_wheelview, null);
		wheelView = (WheelView) view.findViewById(R.id.wheelview);
		wheelView.TEXT_SIZE = 35;
		wheelView.setArrayAdapter(datas, itemIndex, 20);
		setTitle(context.getResources().getString(title));
		setView(view);
		setButton(BUTTON_POSITIVE, context.getString(R.string.ok), this);
		setButton(BUTTON_NEGATIVE, context.getString(R.string.cancel), this);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == AlertDialog.BUTTON_POSITIVE) {
			wheelViewDialogListener.onClickPositive(wheelView.getCurrentItem());
		} else if (which == AlertDialog.BUTTON_NEGATIVE) {
			wheelViewDialogListener.onClickNegative(wheelView.getCurrentItem());
		}
	}

	public interface WheelViewDialogListener {
		void onClickPositive(int selectedIndex);

		void onClickNegative(int selectedIndex);
	}

}
