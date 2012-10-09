package com.juzhai.android.setting.activity;

import android.os.Bundle;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

public class SettingProtocalActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.setting_cell_protocal));
		setNavContentView(R.layout.page_setting_protocal);
	}
}
