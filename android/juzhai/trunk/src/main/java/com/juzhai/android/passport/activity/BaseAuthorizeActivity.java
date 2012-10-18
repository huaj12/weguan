package com.juzhai.android.passport.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.passport.listener.TpAuthorizeListener;

public abstract class BaseAuthorizeActivity extends NavigationActivity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TpAuthorizeListener.AUTHORIZE_REQUEST
				&& (resultCode == TpAuthorizeListener.BIND_SUCCESS_RESULT || resultCode == TpAuthorizeListener.EXPIRED_SUCCESS_RESULT)) {
			// 显示成功提示
			if (resultCode == TpAuthorizeListener.BIND_SUCCESS_RESULT) {
				DialogUtils.showSuccessDialog(this, R.string.bind_ok);
				// new AlertDialog.Builder(this).setMessage("绑定成功")
				// .setCancelable(false).show();
			} else if (resultCode == TpAuthorizeListener.EXPIRED_SUCCESS_RESULT) {
				DialogUtils.showSuccessDialog(this, R.string.authorize_ok);
				// alertDialog = new
				// AlertDialog.Builder(this).setMessage("授权成功")
				// .setCancelable(false).show();
			}
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					popIntent();
				}
			}, 2000);
		}
		super.onActivityResult(requestCode, resultCode, data);
	};

	@Override
	protected void onPause() {
		// if (null != alertDialog) {
		// alertDialog.dismiss();
		// }
		super.onPause();
	}
}
