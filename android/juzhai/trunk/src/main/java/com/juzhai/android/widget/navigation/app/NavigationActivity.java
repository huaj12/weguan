package com.juzhai.android.widget.navigation.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.juzhai.android.R;
import com.juzhai.android.widget.navigation.NavigationBar;

public class NavigationActivity extends ActivityGroup {

	private final static String EXTRA_HAS_PARENT_NAME = "hasParent";

	private LinearLayout rootView;

	private NavigationBar navigationBar;

	private Boolean hasParent;

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
						R.layout.back_button, null);
				// set OnClickListener
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						popIntent();
					}
				});
				navigationBar.setLeftBarButton(button);
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

	protected void pushIntent(Intent intent) {
		// TODO 把this带到下一个NavigationActivity中
		intent.putExtra(EXTRA_HAS_PARENT_NAME, true);
		startActivity(intent);
	}

	protected void popIntent() {
		this.finish();
	}
}
