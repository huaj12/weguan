package com.juzhai.android.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.task.ProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.widget.list.table.widget.UITableView;
import com.juzhai.android.core.widget.list.table.widget.UITableView.ClickListener;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.home.service.impl.HomeService;
import com.juzhai.android.main.activity.TabItemActivity;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;

public class HomeActivity extends TabItemActivity {
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private ImageView userLogoView;
	private TextView nicknameView;
	private TextView userInfoView;
	private TextView logoAuditView;
	private UITableView postTableView;
	private UITableView interestTableView;
	private UITableView interestMeTableView;
	private UITableView inviteTableView;

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
		postTableView = (UITableView) findViewById(R.id.home_post_table_view);
		interestTableView = (UITableView) findViewById(R.id.home_interest_table_view);
		interestMeTableView = (UITableView) findViewById(R.id.home_interest_me_table_view);
		inviteTableView = (UITableView) findViewById(R.id.home_invite_table_view);
		Button edit = (Button) findViewById(R.id.edit_profile_btn);
		showUserInfos();

		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				new ProgressTask(HomeActivity.this, new TaskCallback() {
					@Override
					public void successCallback() {
						showUserInfos();
					}

					@Override
					public String doInBackground() {
						return new HomeService().refresh(HomeActivity.this);
					}
				}, false).execute();
			}
		});
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						HomeSettingActivity.class);
				pushIntent(intent);

			}
		});
	}

	private void showUserInfos() {
		final User user = UserCache.getUserInfo();
		userViewHelper.showUserNewLogo(HomeActivity.this, user, userLogoView,
				logoAuditView, 60, 60);
		userViewHelper.showUserNickname(HomeActivity.this, user, nicknameView);
		userInfoView.setText(user.getUserInfo(HomeActivity.this));

		postTableView.setClickListener(new ClickListener() {

			@Override
			public void onClick(int index) {
				if (index == 0) {
					if (user.getPostCount() > 0) {
						Intent intent = new Intent(HomeActivity.this,
								MyPostActivity.class);
						pushIntent(intent);
					}
				}
			}
		});
		postTableView.addBasicItem(assembly(
				getResources().getString(R.string.my_post_title),
				user.getPostCount()));
		postTableView.commit();

		interestTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					if (user.getInterestUserCount() > 0) {
						Intent intent = new Intent(HomeActivity.this,
								InterestActivity.class);
						pushIntent(intent);
					}
				}
			}
		});
		interestTableView.addBasicItem(assembly(
				getResources().getString(R.string.interest_title),
				user.getInterestUserCount()));
		interestTableView.commit();

		interestMeTableView.setClickListener(new ClickListener() {
			@Override
			public void onClick(int index) {
				if (index == 0) {
					if (user.getInterestMeCount() > 0) {
						Intent intent = new Intent(HomeActivity.this,
								InterestMeActivity.class);
						pushIntent(intent);
					}
				}
			}
		});
		interestMeTableView.addBasicItem(assembly(
				getResources().getString(R.string.interest_me_title),
				user.getInterestMeCount()));
		interestMeTableView.commit();
		inviteTableView.addBasicItem(getResources().getString(
				R.string.invite_friend));
		inviteTableView.commit();

	}

	private String assembly(String name, int count) {
		return getResources().getString(R.string.home_info_layout, name, count);
	}
}
