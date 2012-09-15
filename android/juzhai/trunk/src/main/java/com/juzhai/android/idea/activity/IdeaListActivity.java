package com.juzhai.android.idea.activity;

import android.os.Bundle;

import com.juzhai.android.core.widget.navigation.app.NavigationActivity;

public class IdeaListActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle("出去玩");
	}
}
