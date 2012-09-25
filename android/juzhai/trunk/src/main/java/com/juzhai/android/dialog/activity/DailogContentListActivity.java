/**
 * 
 */
package com.juzhai.android.dialog.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.dialog.adapter.DialogContentListAdapter;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.dialog.task.DialogListGetDataTask;

public class DailogContentListActivity extends NavigationActivity {
	private Dialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = (Dialog) getIntent().getSerializableExtra("dialog");
		if (dialog == null) {
			popIntent();
		}
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.dialog_content_title_begin)
						+ dialog.getTargetUser().getNickname()
						+ getResources().getString(
								R.string.dialog_content_title_end));
		setNavContentView(R.layout.page_dialog_content_list);
		final JuzhaiRefreshListView dialogContentListView = (JuzhaiRefreshListView) findViewById(R.id.dialog_content_list_view);
		dialogContentListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullDownToRefresh(refreshView);
						new DialogListGetDataTask(dialogContentListView)
								.execute(dialog.getTargetUser().getUid(), 1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullUpToRefresh(refreshView);
						new DialogListGetDataTask(dialogContentListView)
								.execute(
										dialog.getTargetUser().getUid(),
										dialogContentListView.getPageAdapter()
												.getPager().getCurrentPage() + 1);
					}
				});
		dialogContentListView.setAdapter(new DialogContentListAdapter(dialog
				.getTargetUser(), DailogContentListActivity.this));
		dialogContentListView.manualRefresh();
	}

}
