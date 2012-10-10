package com.juzhai.android.home.activity;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.model.User;

public class HomeActivity extends NavigationActivity {
	private String refeshUri = "home/refresh";
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private ImageView userLogoView;
	private TextView nicknameView;
	private TextView userInfoView;
	private TextView logoAuditView;
	private RelativeLayout interestMeLayout;
	private RelativeLayout interestLayout;
	private RelativeLayout myPostLayout;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_home);
		Button refreshBtn = (Button) getLayoutInflater().inflate(
				R.layout.button_refresh, null);

		getNavigationBar().setBarTitle(
				getResources().getString(R.string.tabitem_home));
		getNavigationBar().setRightView(refreshBtn);
		userLogoView = (ImageView) findViewById(R.id.user_logo);
		nicknameView = (TextView) findViewById(R.id.user_nickname);
		userInfoView = (TextView) findViewById(R.id.user_info);
		logoAuditView = (TextView) findViewById(R.id.logo_audit);
		interestMeLayout = (RelativeLayout) findViewById(R.id.home_interest_me_layout);
		interestLayout = (RelativeLayout) findViewById(R.id.home_my_interest_layout);
		myPostLayout = (RelativeLayout) findViewById(R.id.home_my_post_layout);
		refresh();

		refreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... params) {

						ResponseEntity<UserResult> responseEntity = null;
						try {
							responseEntity = HttpUtils.get(HomeActivity.this,
									refeshUri, UserCache.getUserStatus(),
									UserResult.class);
						} catch (Exception e) {
							return getResources().getString(
									R.string.system_internet_erorr);
						}
						UserResult result = responseEntity.getBody();
						if (!result.getSuccess()) {
							return result.getErrorInfo();
						} else {
							UserCacheManager.updateUserCache(result.getResult());
						}
						return null;
					}

					@Override
					protected void onPreExecute() {
						if (progressDialog != null) {
							progressDialog.show();
						} else {
							progressDialog = ProgressDialog.show(
									HomeActivity.this,
									getResources().getString(R.string.sending),
									getResources().getString(
											R.string.please_wait), true, false);
						}
						super.onPreExecute();
					}

					@Override
					protected void onPostExecute(String errorInfo) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						if (StringUtils.isNotEmpty(errorInfo)) {
							DialogUtils.showToastText(HomeActivity.this,
									errorInfo);
						} else {
							DialogUtils.showToastText(HomeActivity.this,
									R.string.success);
							refresh();
						}
					}

				}.execute();
			}
		});
	}

	private void refresh() {
		User user = UserCache.getUserInfo();
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
