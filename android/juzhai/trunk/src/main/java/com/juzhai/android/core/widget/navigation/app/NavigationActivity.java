package com.juzhai.android.core.widget.navigation.app;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.core.widget.navigation.NavigationBar;

public class NavigationActivity extends BaseActivity {
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
}
