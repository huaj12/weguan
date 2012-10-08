/**
 * 
 */
package com.juzhai.android.home.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.adapter.MyPostsAdapter;
import com.juzhai.android.home.task.MyPostsListGetDataTask;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.model.Post;

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

		postsListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> item, View view,
					int position, final long id) {
				new AlertDialog.Builder(MyPostActivity.this)
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
