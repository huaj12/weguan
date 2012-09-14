package com.juzhai.android.post.activity;

import android.os.Bundle;

import com.juzhai.android.widget.navigation.app.NavigationActivity;

public class PostDetailActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle("拒宅详情");
	}
}
