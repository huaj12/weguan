package com.juzhai.android.home.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.ActivityCode;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.adapter.UserPostAdapter;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.home.task.UserPostListGetDataTask;
import com.juzhai.android.passport.activity.AuthorizeBindActivity;
import com.juzhai.android.passport.activity.AuthorizeExpiredActivity;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.activity.PostDetailActivity;

public class ZhaobanActivity extends NavigationActivity {
	private ZhaobanOrder order = ZhaobanOrder.ONLINE;
	private Integer gender = null;
	private JuzhaiRefreshListView postListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_user_post_list);
		Button sendJzButton = (Button) getLayoutInflater().inflate(
				R.layout.button_send_jz, null);
		sendJzButton.setOnClickListener(null);
		getNavigationBar().setLeftView(sendJzButton);
		SegmentedButton segmentedButton = new SegmentedButton(this,
				getResources().getStringArray(R.array.post_head_tab_item), 80,
				32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						switch (which) {
						case 0:
							order = ZhaobanOrder.ONLINE;
							break;
						case 1:
							order = ZhaobanOrder.NEW;
							break;
						}
						postListView.manualRefresh();
					}
				});
		getNavigationBar().setBarTitleView(segmentedButton);
		final Button genderBtn = (Button) getLayoutInflater().inflate(
				R.layout.button_gender, null);
		genderBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(ZhaobanActivity.this)
						.setTitle(
								getResources()
										.getString(R.string.select_gender))
						.setItems(R.array.select_gender_item,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											gender = null;
											genderBtn
													.setBackgroundResource(R.drawable.gender_selector_button);
											break;
										case 1:
											gender = 1;
											genderBtn
													.setBackgroundResource(R.drawable.boy_selector_button);
											break;
										case 2:
											gender = 0;
											genderBtn
													.setBackgroundResource(R.drawable.girl_selector_button);
											break;
										}
										postListView.manualRefresh();
										dialog.cancel();
									}

								}).show();
			}
		});
		getNavigationBar().setRightView(genderBtn);

		postListView = (JuzhaiRefreshListView) findViewById(R.id.user_post_list);
		postListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new UserPostListGetDataTask(postListView).execute(gender,
						order, 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new UserPostListGetDataTask(postListView).execute(gender,
						order, postListView.getPageAdapter().getPager()
								.getCurrentPage() + 1);
			}
		});
		postListView.setAdapter(new UserPostAdapter(ZhaobanActivity.this));
		postListView.manualRefresh();

		User user = UserCache.getUserInfo();
		if (user.getTpId() > 0 && user.isTokenExpired()) {
			pushIntent(new Intent(ZhaobanActivity.this,
					AuthorizeExpiredActivity.class));
		} else if (user.getTpId() <= 0) {
			// 提示授权
			pushIntent(new Intent(ZhaobanActivity.this,
					AuthorizeBindActivity.class));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityCode.RequestCode.ZHAOBAN_LIST_REQUEST_CODE
				&& resultCode == ActivityCode.ResultCode.ZHAOBAN_LIST_RESULT_CODE) {
			User user = (User) data.getSerializableExtra("user");
			int position = data.getIntExtra("position", -1);
			if (position >= 0 && user != null) {
				postListView.getPageAdapter().replaceData(position, user);
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
