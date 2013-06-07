/**
 * 
 */
package com.easylife.weather.core.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;

/**
 * @author kooks
 * 
 */
public class BaseActivity extends Activity {

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

}
