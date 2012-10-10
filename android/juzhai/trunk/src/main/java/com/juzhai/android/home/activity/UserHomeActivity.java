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
import com.juzhai.android.core.task.PostTask;
import com.juzhai.android.core.task.TaskSuccessCallBack;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.home.adapter.MyPostsAdapter;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.home.task.MyPostsListGetDataTask;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.activity.PostDetailActivity;

/**
 * @author kooks
 * 
 */
public class UserHomeActivity extends NavigationActivity {
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private String interestUri = "home/interest";
	private String unInterestUri = "home/removeInterest";
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = (User) getIntent().getSerializableExtra("targetUser");
		setNavContentView(R.layout.page_user_home);
		getNavigationBar().setBarTitle(
				user.getNickname()
						+ getResources().getString(R.string.user_home_title));
		final ImageView userLogoView = (ImageView) findViewById(R.id.user_logo);
		TextView nicknameView = (TextView) findViewById(R.id.user_nickname);
		TextView userInfoView = (TextView) findViewById(R.id.user_info);
		Button contactBtn = (Button) findViewById(R.id.contact);
		final Button interestBtn = (Button) findViewById(R.id.interest);
		final Button unInterestBtn = (Button) findViewById(R.id.un_interest);
		final TextView postCountView = (TextView) findViewById(R.id.home_post_count);
		userViewHelper.showUserLogo(UserHomeActivity.this, user, userLogoView,
				60, 60);
		userViewHelper.showUserNickname(UserHomeActivity.this, user,
				nicknameView);
		userInfoView.setText(user.getUserInfo(UserHomeActivity.this));
		postCountView.setText(getResources().getString(
				R.string.user_home_post_count_begin)
				+ 0
				+ getResources().getString(R.string.user_home_post_count_end));
		userLogoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserHomeActivity.this,
						PreviewActivity.class);
				intent.putExtra("targetUser", user);
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
		final JuzhaiRefreshListView postsListView = (JuzhaiRefreshListView) findViewById(R.id.my_posts_list_view);
		postsListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new MyPostsListGetDataTask(UserHomeActivity.this,
						postsListView, postCountView).execute(user.getUid(), 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new MyPostsListGetDataTask(UserHomeActivity.this, postsListView)
						.execute(user.getUid(), postsListView.getPageAdapter()
								.getPager().getCurrentPage() + 1);
			}
		});
		postsListView.setAdapter(new MyPostsAdapter(UserHomeActivity.this));
		postsListView.manualRefresh();
		postsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> item, View view, int arg2,
					long id) {
				int position = (int) id;
				Post post = (Post) postsListView.getPageAdapter().getItem(
						position);
				Intent intent = new Intent(UserHomeActivity.this,
						PostDetailActivity.class);
				user.setPostView(post);
				intent.putExtra("user", user);
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
			final Map<String, String> values = new HashMap<String, String>();
			values.put("uid", String.valueOf(user.getUid()));
			unInterestBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DialogUtils.showConfirmDialog(UserHomeActivity.this,
							new PostTask(unInterestUri, UserHomeActivity.this,
									values, new TaskSuccessCallBack() {
										@Override
										public void callback() {
											unInterestBtn
													.setVisibility(View.GONE);
											interestBtn
													.setVisibility(View.VISIBLE);
										}
									}));
				}
			});
			interestBtn.setOnClickListener(new SimpleClickListener(interestUri,
					UserHomeActivity.this, values, new TaskSuccessCallBack() {
						@Override
						public void callback() {
							unInterestBtn.setVisibility(View.VISIBLE);
							interestBtn.setVisibility(View.GONE);
						}
					}));
		}
	}
}
