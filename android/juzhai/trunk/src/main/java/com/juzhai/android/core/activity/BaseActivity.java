/**
 * 
 */
package com.juzhai.android.core.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.juzhai.android.R;
import com.umeng.analytics.MobclickAgent;

/**
 * @author kooks
 * 
 */
public class BaseActivity extends Activity {

	public void clearStackAndStartActivity(Intent intent) {
		setResult(ActivityCode.ResultCode.CLEAR_RESULT_CODE);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.CLEAR_REQUEST_CODE
				&& resultCode == ActivityCode.ResultCode.CLEAR_RESULT_CODE) {
			setResult(ActivityCode.ResultCode.CLEAR_RESULT_CODE);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void openKeyboard(final View view) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(view,
						InputMethodManager.RESULT_UNCHANGED_SHOWN);
			}
		}, 300);
	}

	protected void closeKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	protected void uploadImageDialogAnim() {
		overridePendingTransition(R.anim.dialog_up_in, R.anim.dialog_up_out);
	}

}
