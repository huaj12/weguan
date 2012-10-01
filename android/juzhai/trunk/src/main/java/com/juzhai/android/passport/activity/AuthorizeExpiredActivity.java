package com.juzhai.android.passport.activity;

import android.os.Bundle;

import com.juzhai.android.R;

public class AuthorizeExpiredActivity extends BaseAuthorizeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.authorize_title));
	}
}
