package com.juzhai.android.dialog.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.dialog.adapter.DialogListAdapter;
import com.juzhai.android.dialog.task.DialogListGetDataTask;

public class DialogListActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.dialog_title));
		setNavContentView(R.layout.page_dialog_list);
		final JuzhaiRefreshListView dialogListView = (JuzhaiRefreshListView) findViewById(R.id.dialog_list_view);

		dialogListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new DialogListGetDataTask(dialogListView).execute(1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new DialogListGetDataTask(dialogListView)
						.execute(dialogListView.getPageAdapter().getPager()
								.getCurrentPage() + 1);
			}
		});
		dialogListView
				.setAdapter(new DialogListAdapter(DialogListActivity.this));

		dialogListView.manualRefresh();
	}

}
