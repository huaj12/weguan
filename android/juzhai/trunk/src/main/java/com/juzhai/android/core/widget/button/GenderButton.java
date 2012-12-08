package com.juzhai.android.core.widget.button;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.juzhai.android.R;

public class GenderButton extends Button {
	// TODO (done) 能不能面向对象一点，子类化一个GenderButton，能很容易的使用
	public GenderButton(Context context, GenderButtonCallback callback) {
		super(context);
		init(context, callback);
	}

	private void init(final Context context, final GenderButtonCallback callback) {
		this.setBackgroundResource(R.drawable.gender_selector_button);
		final Button btn = this;
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
						.setTitle(
								getResources()
										.getString(R.string.select_gender))
						.setItems(R.array.select_gender_item,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										Integer selectGender = null;
										switch (which) {
										case 0:
											selectGender = null;
											btn.setBackgroundResource(R.drawable.gender_selector_button);
											break;
										case 1:
											selectGender = 1;
											btn.setBackgroundResource(R.drawable.boy_selector_button);
											break;
										case 2:
											selectGender = 0;
											btn.setBackgroundResource(R.drawable.girl_selector_button);
											break;
										}
										callback.onClickCallback(selectGender);

									}
								}).show();
			}
		});
	}

	public interface GenderButtonCallback {

		void onClickCallback(Integer selectGender);
	}
}
