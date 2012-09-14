package com.juzhai.android.passport.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.juzhai.android.passport.activity.WebViewActivity;

public class TpLoginListener implements OnItemClickListener {
	private Context mContext;

	public TpLoginListener(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, WebViewActivity.class);
		switch (position) {
		case 0:
			intent.putExtra("tpId", "6");
			break;
		case 1:
			intent.putExtra("tpId", "7");
			break;
		case 2:
			intent.putExtra("tpId", "8");
			break;
		}
		mContext.startActivity(intent);

	}
}
