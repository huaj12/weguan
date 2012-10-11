package com.juzhai.android.main.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

public class TabItemActivity extends NavigationActivity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
