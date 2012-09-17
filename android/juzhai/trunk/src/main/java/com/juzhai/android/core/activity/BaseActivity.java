/**
 * 
 */
package com.juzhai.android.core.activity;

import android.app.ActivityGroup;
import android.content.Intent;

/**
 * @author kooks
 * 
 */
public class BaseActivity extends ActivityGroup {
	protected final static String EXTRA_HAS_PARENT_NAME = "hasParent";
	private final static int REQUEST_CODE = 1;
	private final static int RESULT_CODE = 2;

	protected void pushIntent(Intent intent) {
		// TODO 把this带到下一个NavigationActivity中
		intent.putExtra(EXTRA_HAS_PARENT_NAME, true);
		startActivityForResult(intent, REQUEST_CODE);
	}

	protected void popAllIntent() {
		setResult(RESULT_CODE);
		this.finish();
	}

	protected void popIntent() {
		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
			setResult(RESULT_CODE);
			this.finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
