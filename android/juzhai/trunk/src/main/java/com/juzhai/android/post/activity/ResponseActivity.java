/**
 * 
 */
package com.juzhai.android.post.activity;

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
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.home.adapter.InterestUserListAdapter;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.task.ResponseListGetDataTask;

public class ResponseActivity extends NavigationActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_response);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.post_response_user_title));
		final long postId = getIntent().getLongExtra("postId", 0);
		final JuzhaiRefreshListView responseListView = (JuzhaiRefreshListView) findViewById(R.id.interest_list_view);
		responseListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullDownToRefresh(refreshView);
						new ResponseListGetDataTask(ResponseActivity.this,
								responseListView).execute(postId, 1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullUpToRefresh(refreshView);
						new ResponseListGetDataTask(ResponseActivity.this,
								responseListView).execute(postId,
								responseListView.getPageAdapter().getPager()
										.getCurrentPage() + 1);
					}
				});
		responseListView.setAdapter(new InterestUserListAdapter(
				ResponseActivity.this));
		responseListView.manualRefresh();

		responseListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				int location = (int) id;
				User user = (User) responseListView.getPageAdapter().getItem(
						location);
				Intent intent = new Intent(ResponseActivity.this,
						UserHomeActivity.class);
				intent.putExtra("uid", user.getUid());
				pushIntent(intent);
			}
		});

	}
}
