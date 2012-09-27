package com.juzhai.android.passport.listener;

import android.content.Intent;

import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.activity.WebViewActivity;

public class TpLoginListener implements ClickListener {

	private NavigationActivity activity;

	public TpLoginListener(NavigationActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(int index) {
		int tpId = 0;
		switch (index) {
		case 0:
			tpId = 6;
			break;
		case 1:
			tpId = 8;
			break;
		case 2:
			tpId = 7;
			break;
		}
		Intent intent = new Intent(activity, WebViewActivity.class);
		intent.putExtra("tpId", tpId);
		activity.pushIntentForResult(intent, BaseActivity.CLEAR_REQUEST_CODE);

	}
}
