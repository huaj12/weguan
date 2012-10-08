/**
 * 
 */
package com.juzhai.android.core.activity;

import android.app.Activity;
import android.content.Intent;

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

}
