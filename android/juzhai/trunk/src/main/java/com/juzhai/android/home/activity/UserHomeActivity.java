/**
 * 
 */
package com.juzhai.android.home.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.PreviewActivity;
import com.juzhai.android.core.listener.SimpleClickListener;
import com.juzhai.android.core.stat.UmengEvent;
import com.juzhai.android.core.task.PostProgressTask;
import com.juzhai.android.core.task.ProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.dialog.SuccessPromptDialog;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.home.adapter.MyPostsAdapter;
import com.juzhai.android.home.exception.HomeException;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.home.service.IHomeService;
import com.juzhai.android.home.service.impl.HomeService;
import com.juzhai.android.home.task.MyPostsListGetDataTask;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.activity.PostDetailActivity;
import com.juzhai.android.post.model.Post;
import com.umeng.analytics.MobclickAgent;

/**
 * @author kooks
 * 
 */
public class UserHomeActivity extends NavigationActivity {
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private String interestUri = "home/interest";
	private String unInterestUri = "home/removeInterest";
	private User user;
	private IHomeService homeService = new HomeService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_user_home);

		// final User user = (User)
		// getIntent().getSerializableExtra("targetUser");
		final long uid = getIntent().getLongExtra("uid", 0L);
		if (uid <= 0) {
			return;
		}
		new ProgressTask(UserHomeActivity.this, new TaskCallback() {
			@Override
			public void successCallback() {
				if (user == null) {
					return;
				}
				getNavigationBar().setBarTitle(
						user.getNickname()
								+ getResources().getString(
										R.string.user_home_title));
				final ImageView userLogoView = (ImageView) findViewById(R.id.user_logo);
				TextView nicknameView = (TextView) findViewById(R.id.user_nickname);
				TextView userInfoView = (TextView) findViewById(R.id.user_info);
				Button contactBtn = (Button) findViewById(R.id.contact);
				final Button interestBtn = (Button) findViewById(R.id.interest);
				final Button unInterestBtn = (Button) findViewById(R.id.un_interest);
				userViewHelper.showUserLogo(UserHomeActivity.this, user,
						userLogoView, 60, 60);
				userViewHelper.showUserNickname(UserHomeActivity.this, user,
						nicknameView);
				userInfoView.setText(user.getUserInfo(UserHomeActivity.this));
				userLogoView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(UserHomeActivity.this,
								PreviewActivity.class);
						intent.putExtra("defaultImage",
								R.drawable.user_face_unload);
						intent.putExtra("imageUrl", user.getOriginalLogo());
						pushIntent(intent);
					}
				});
				contactBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(UserHomeActivity.this,
								DialogContentListActivity.class);
						intent.putExtra("targetUser", user);
						pushIntent(intent);
					}

				});
				if (user.getUid() != UserCache.getUid()) {
					if (user.isHasInterest()) {
						unInterestBtn.setVisibility(View.VISIBLE);
						interestBtn.setVisibility(View.GONE);
					} else {
						unInterestBtn.setVisibility(View.GONE);
						interestBtn.setVisibility(View.VISIBLE);
					}
					final Map<String, Object> values = new HashMap<String, Object>();
					values.put("uid", user.getUid());
					unInterestBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							DialogUtils.showConfirmDialog(
									UserHomeActivity.this,
									new PostProgressTask(UserHomeActivity.this,
											unInterestUri, values,
											new TaskCallback() {
												@Override
												public void successCallback() {
													unInterestBtn
															.setVisibility(View.GONE);
													interestBtn
															.setVisibility(View.VISIBLE);
												}

												@Override
												public String doInBackground() {
													return null;
												}
											}),

									R.string.un_interest_confirm);
						}
					});
					interestBtn.setOnClickListener(new SimpleClickListener(
							interestUri, UserHomeActivity.this, values, false,
							new TaskCallback() {
								@Override
								public void successCallback() {
									new SuccessPromptDialog(
											UserHomeActivity.this,
											R.string.user_interest_success)
											.show();
									unInterestBtn.setVisibility(View.VISIBLE);
									interestBtn.setVisibility(View.GONE);
									MobclickAgent.onEvent(
											UserHomeActivity.this,
											UmengEvent.INTEREST_USER);
								}

								@Override
								public String doInBackground() {
									return null;
								}
							}));
				} else {
					contactBtn.setVisibility(View.GONE);
					unInterestBtn.setVisibility(View.GONE);
					interestBtn.setVisibility(View.GONE);
				}

				final TextView postCountView = (TextView) findViewById(R.id.home_post_count);
				postCountView.setText(getResources().getString(
						R.string.user_home_post_count, 0));
				final JuzhaiRefreshListView postsListView = (JuzhaiRefreshListView) findViewById(R.id.my_posts_list_view);
				postsListView
						.setOnRefreshListener(new OnRefreshListener2<ListView>() {
							@Override
							public void onPullDownToRefresh(
									PullToRefreshBase<ListView> refreshView) {
								super.onPullDownToRefresh(refreshView);
								new MyPostsListGetDataTask(
										UserHomeActivity.this, postsListView,
										postCountView).execute(user.getUid(), 1);
							}

							@Override
							public void onPullUpToRefresh(
									PullToRefreshBase<ListView> refreshView) {
								super.onPullUpToRefresh(refreshView);
								new MyPostsListGetDataTask(
										UserHomeActivity.this, postsListView)
										.execute(user.getUid(), postsListView
												.getPageAdapter().getPager()
												.getCurrentPage() + 1);
							}
						});
				postsListView.setAdapter(new MyPostsAdapter(
						UserHomeActivity.this));
				postsListView.manualRefresh();
				postsListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> item, View view,
							int arg2, long id) {
						int position = (int) id;
						Post post = (Post) postsListView.getPageAdapter()
								.getItem(position);
						Intent intent = new Intent(UserHomeActivity.this,
								PostDetailActivity.class);
						user.setPostView(post);
						intent.putExtra("user", user);
						pushIntent(intent);
					}

				});
			}

			@Override
			public String doInBackground() {
				try {
					user = homeService.getUserInfo(UserHomeActivity.this, uid);
				} catch (HomeException e) {
					return e.getMessage();
				}
				return null;
			}
		}, false).execute();
	}
}
