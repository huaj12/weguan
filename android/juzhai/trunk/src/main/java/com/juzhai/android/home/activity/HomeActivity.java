package com.juzhai.android.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public class HomeActivity extends NavigationActivity {
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private User user = UserCache.getUserInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_home);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.tabitem_home));
		ImageView userLogoView = (ImageView) findViewById(R.id.user_logo);
		TextView nicknameView = (TextView) findViewById(R.id.user_nickname);
		TextView userInfoView = (TextView) findViewById(R.id.user_info);
		TextView logoAuditView = (TextView) findViewById(R.id.logo_audit);
		RelativeLayout interestMeLayout = (RelativeLayout) findViewById(R.id.home_interest_me_layout);
		RelativeLayout interestLayout = (RelativeLayout) findViewById(R.id.home_my_interest_layout);
		RelativeLayout myPostLayout = (RelativeLayout) findViewById(R.id.home_my_post_layout);
		userViewHelper.showUserNewLogo(HomeActivity.this, user, userLogoView,
				logoAuditView, 60, 60);
		userViewHelper.showUserNickname(HomeActivity.this, user, nicknameView);
		userInfoView.setText(user.getUserInfo(HomeActivity.this));
		TextView myPostView = (TextView) findViewById(R.id.home_my_post);
		TextView myInterestView = (TextView) findViewById(R.id.home_my_interest);
		TextView myInterestMeView = (TextView) findViewById(R.id.home_interest_me);
		// TODO (review) 出现了多个括号 可以尝试使用string format
		myPostView.setText(getResources().getString(R.string.my_post_title)
				+ " (" + user.getPostCount() + ")");
		myInterestView.setText(getResources()
				.getString(R.string.interest_title)
				+ " ("
				+ user.getInterestUserCount() + ")");
		myInterestMeView.setText(getResources().getString(
				R.string.interest_me_title)
				+ " (" + user.getInterestMeCount() + ")");
		if (user.getInterestUserCount() > 0) {
			interestLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity.this,
							InterestActivity.class);
					pushIntent(intent);
				}
			});
		}
		if (user.getInterestMeCount() > 0) {
			interestMeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity.this,
							InterestMeActivity.class);
					pushIntent(intent);
				}
			});
		}
		if (user.getPostCount() > 0) {
			myPostLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity.this,
							MyPostActivity.class);
					pushIntent(intent);
				}
			});
		}
	}
}
