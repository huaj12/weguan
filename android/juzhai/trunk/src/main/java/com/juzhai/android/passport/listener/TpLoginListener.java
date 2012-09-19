package com.juzhai.android.passport.listener;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.activity.WebViewActivity;

public class TpLoginListener implements OnItemClickListener {

	private NavigationActivity activity;

	public TpLoginListener(NavigationActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(activity, WebViewActivity.class);
		intent.putExtra("tpId", (Integer) view.getTag());
		activity.pushIntentForResult(intent, BaseActivity.CLEAR_REQUEST_CODE);

	}
}
