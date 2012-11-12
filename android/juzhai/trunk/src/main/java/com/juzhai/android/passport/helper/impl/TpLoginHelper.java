package com.juzhai.android.passport.helper.impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.activity.TpLoginActivity;
import com.juzhai.android.passport.helper.ITpLoginHelper;

public class TpLoginHelper implements ITpLoginHelper {

	@Override
	public void addTpLoginListener(Context context) {
		final NavigationActivity activity = (NavigationActivity) context;
		Button sinaBtn = (Button) activity.findViewById(R.id.login_sina_btn);
		Button qqBtn = (Button) activity.findViewById(R.id.login_qq_btn);
		Button dbBtn = (Button) activity.findViewById(R.id.login_db_btn);
		dbBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(activity, 7l);
			}
		});
		qqBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(activity, 8l);
			}
		});
		sinaBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(activity, 6l);
			}
		});
	}

	private void startActivity(NavigationActivity activity, long tpId) {
		Intent intent = new Intent(activity, TpLoginActivity.class);
		intent.putExtra("tpId", tpId);
		activity.pushIntentForResult(intent,
				ActivityCode.RequestCode.CLEAR_REQUEST_CODE);
	}

}
