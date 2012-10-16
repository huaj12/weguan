/**
 * 
 */
package com.juzhai.android.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.adapter.MyPostsAdapter;
import com.juzhai.android.home.task.MyPostsListGetDataTask;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.activity.PostDetailActivity;
import com.juzhai.android.post.model.Post;

/**
 * @author kooks
 * 
 */
public class MyPostActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_my_posts);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.my_post_title));

		final JuzhaiRefreshListView postsListView = (JuzhaiRefreshListView) findViewById(R.id.my_posts_list_view);
		postsListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new MyPostsListGetDataTask(MyPostActivity.this, postsListView)
						.execute(UserCache.getUid(), 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new MyPostsListGetDataTask(MyPostActivity.this, postsListView)
						.execute(UserCache.getUid(),
								postsListView.getPageAdapter().getPager()
										.getCurrentPage() + 1);
			}
		});
		postsListView.setAdapter(new MyPostsAdapter(MyPostActivity.this));
		postsListView.manualRefresh();

		postsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> item, View view, int arg2,
					long id) {
				int position = (int) id;
				Post post = (Post) postsListView.getPageAdapter().getItem(
						position);
				Intent intent = new Intent(MyPostActivity.this,
						PostDetailActivity.class);
				User user = UserCache.getUserInfo();
				user.setPostView(post);
				intent.putExtra("user", user);
				pushIntent(intent);
			}

		});
	}
}
