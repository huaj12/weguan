/**
 * 
 */
package com.juzhai.android.home.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.task.PostTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.home.adapter.MyPostsAdapter;
import com.juzhai.android.home.helper.IUserViewHelper;
import com.juzhai.android.home.helper.impl.UserViewHelper;
import com.juzhai.android.home.task.MyPostsListGetDataTask;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.passport.model.User;

/**
 * @author kooks
 * 
 */
public class UserHomeActivity extends NavigationActivity {
	private IUserViewHelper userViewHelper = new UserViewHelper();
	private String interestUri = "home/interest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final User user = (User) getIntent().getSerializableExtra("targetUser");
		setNavContentView(R.layout.page_user_home);
		getNavigationBar().setBarTitle(
				user.getNickname()
						+ getResources().getString(R.string.user_home_title));
		ImageView userLogoView = (ImageView) findViewById(R.id.user_logo);
		TextView nicknameView = (TextView) findViewById(R.id.user_nickname);
		TextView userInfoView = (TextView) findViewById(R.id.user_info);
		Button contactBtn = (Button) findViewById(R.id.contact);
		Button interestBtn = (Button) findViewById(R.id.interest);
		final TextView postCountView = (TextView) findViewById(R.id.home_post_count);
		userViewHelper.showUserLogo(UserHomeActivity.this, user, userLogoView,
				60, 60);
		userViewHelper.showUserNickname(UserHomeActivity.this, user,
				nicknameView);
		userInfoView.setText(user.getUserInfo(UserHomeActivity.this));
		//TODO (review) "..."是什么意思
		postCountView.setText(getResources().getString(
				R.string.user_home_post_count_begin)
				+ "..."
				+ getResources().getString(R.string.user_home_post_count_end));
		contactBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserHomeActivity.this,
						DialogContentListActivity.class);
				intent.putExtra("targetUser", user);
				pushIntent(intent);
			}

		});
		interestBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO (review) 为什么不用SimpleClickListener
				Map<String, String> values = new HashMap<String, String>();
				values.put("uid", String.valueOf(user.getUid()));
				new PostTask(interestUri, UserHomeActivity.this, values, null)
						.execute();
			}
		});
		final JuzhaiRefreshListView postsListView = (JuzhaiRefreshListView) findViewById(R.id.my_posts_list_view);
		postsListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new MyPostsListGetDataTask(postsListView,
						UserHomeActivity.this, postCountView).execute(
						user.getUid(), 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new MyPostsListGetDataTask(postsListView).execute(
						user.getUid(), postsListView.getPageAdapter()
								.getPager().getCurrentPage() + 1);
			}
		});
		postsListView.setAdapter(new MyPostsAdapter(UserHomeActivity.this));
		postsListView.manualRefresh();

		//TODO (review) 你准备修改和删除他人的拒宅？
		postsListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> item, View view,
					int position, final long id) {
				new AlertDialog.Builder(UserHomeActivity.this)
						.setTitle(R.string.operating)
						.setItems(
								new String[] {
										getResources().getString(
												R.string.del_post),
										getResources().getString(
												R.string.edit_post),
										getResources().getString(
												R.string.cancel) },
								new OnClickListener() {

									@Override
									public void onClick(
											final DialogInterface dialog,
											int which) {
										dialog.cancel();
										final int location = (int) id;
										Post post = (Post) postsListView
												.getPageAdapter().getItem(
														location);
										switch (which) {
										case 0:
											// 删除拒宅
											break;
										case 1:
											// 编辑拒宅
											break;
										}

									}
								}).show();
				return false;
			}
		});
	}
}
