package com.juzhai.android.core.widget.navigation.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.core.widget.navigation.NavigationBar;

public class NavigationActivity extends BaseActivity {

	protected final static String EXTRA_HAS_PARENT_NAME = "hasParent";

	private LinearLayout rootView;

	private NavigationBar navigationBar;

	private Boolean hasParent;

	protected OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			popIntent();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hasParent = getIntent().getBooleanExtra(EXTRA_HAS_PARENT_NAME, false);
		if (rootView == null) {
			rootView = new LinearLayout(this);
			rootView.setOrientation(LinearLayout.VERTICAL);
			setContentView(rootView);
		}
		if (navigationBar == null) {
			navigationBar = new NavigationBar(this);
			if (hasParent) {
				Button button = (Button) getLayoutInflater().inflate(
						R.layout.button_back, null);
				// set OnClickListener
				button.setOnClickListener(backClickListener);
				navigationBar.setLeftView(button);
			}
			rootView.addView(navigationBar);
		}
	}

	protected void setNavContentView(View view) {
		rootView.addView(view);
	}

	protected void setNavContentView(int resource) {
		rootView.addView(getLayoutInflater().inflate(resource, null));
	}

	protected NavigationBar getNavigationBar() {
		return navigationBar;
	}

	public void pushIntent(Intent intent) {
		intent.putExtra(EXTRA_HAS_PARENT_NAME, true);
		startActivity(intent);
	}

	public void pushIntentForResult(Intent intent, int requestCode) {
		intent.putExtra(EXTRA_HAS_PARENT_NAME, true);
		startActivityForResult(intent, requestCode);
	}

	public void popIntent() {
		this.finish();
	}

	protected boolean pressBackToHome(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);
			return true;
		}
		return false;
	}

	protected Button setRightFinishButton() {
		Button finish = (Button) getLayoutInflater().inflate(
				R.layout.button_finish, null);
		navigationBar.setRightView(finish);
		return finish;
	}
}
