package com.juzhai.android.dialog.activity;

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
import com.juzhai.android.dialog.adapter.DialogListAdapter;
import com.juzhai.android.dialog.model.Dialog;
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
		dialogListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> item, View view,
					int position, long id) {
				Dialog dialog = (Dialog) item.getItemAtPosition(position);
				Intent intent = new Intent(DialogListActivity.this,
						DailogContentListActivity.class);
				intent.putExtra("dialog", dialog);
				pushIntent(intent);
			}

		});
		dialogListView.manualRefresh();
	}

}
