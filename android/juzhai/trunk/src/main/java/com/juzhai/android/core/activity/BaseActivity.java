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
	public final static int CLEAR_REQUEST_CODE = 1;
	public final static int CLEAR_RESULT_CODE = 2;
	public final static int IDEA_LIST_REQUEST_CODE = 3;

	public void clearStackAndStartActivity(Intent intent) {
		setResult(CLEAR_RESULT_CODE);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CLEAR_REQUEST_CODE
				&& resultCode == CLEAR_RESULT_CODE) {
			setResult(CLEAR_RESULT_CODE);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
