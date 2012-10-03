package com.juzhai.android.passport.listener;

import android.content.Intent;

import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.activity.TpLoginActivity;

public class TpAuthorizeListener implements ClickListener {

	public final static int AUTHORIZE_REQUEST = 10;
	public final static int BIND_SUCCESS_RESULT = 10;
	public final static int EXPIRED_SUCCESS_RESULT = 11;

	private NavigationActivity activity;

	public TpAuthorizeListener(NavigationActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(int index) {
		long tpId = 0L;
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
		Intent intent = new Intent(activity, TpLoginActivity.class);
		intent.putExtra("tpId", tpId);
		intent.putExtra("authorizeType", 2);
		activity.pushIntentForResult(intent, AUTHORIZE_REQUEST);

	}
}
