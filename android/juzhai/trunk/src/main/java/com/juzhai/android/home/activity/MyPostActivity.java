/**
 * 
 */
package com.juzhai.android.home.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.adapter.MyPostsAdapter;
import com.juzhai.android.home.task.MyPostsListGetDataTask;
import com.juzhai.android.passport.data.UserCache;

/**
 * @author kooks
 * 
 */
public class MyPostActivity extends NavigationActivity {
	private JuzhaiRefreshListView postsListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_my_posts);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.my_post_title));

		postsListView = (JuzhaiRefreshListView) findViewById(R.id.my_posts_list_view);
		postsListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new MyPostsListGetDataTask(postsListView).execute(
						UserCache.getUid(), 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new MyPostsListGetDataTask(postsListView).execute(
						UserCache.getUid(), postsListView.getPageAdapter()
								.getPager().getCurrentPage() + 1);
			}
		});
		postsListView.setAdapter(new MyPostsAdapter(MyPostActivity.this));
		postsListView.manualRefresh();

	}
}
