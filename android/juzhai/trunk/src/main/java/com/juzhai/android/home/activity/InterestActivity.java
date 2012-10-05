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
import com.juzhai.android.home.adapter.InterestUserListAdapter;
import com.juzhai.android.home.task.InterestListGetDataTask;

/**
 * @author kooks
 * 
 */
public class InterestActivity extends NavigationActivity {
	private JuzhaiRefreshListView interestListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_interest);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.interest_title));

		interestListView = (JuzhaiRefreshListView) findViewById(R.id.interest_list_view);
		interestListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullDownToRefresh(refreshView);
						new InterestListGetDataTask(interestListView)
								.execute(1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullUpToRefresh(refreshView);
						new InterestListGetDataTask(interestListView)
								.execute(interestListView.getPageAdapter()
										.getPager().getCurrentPage() + 1);
					}
				});
		interestListView.setAdapter(new InterestUserListAdapter(
				InterestActivity.this));
		interestListView.manualRefresh();

	}

}
