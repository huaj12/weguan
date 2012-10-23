package com.juzhai.android.dialog.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.task.PostProgressTask;
import com.juzhai.android.core.task.TaskCallback;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.dialog.adapter.DialogListAdapter;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.dialog.task.DialogListGetDataTask;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.main.activity.TabItemActivity;

public class DialogListActivity extends TabItemActivity {
	private boolean hasLoad;
	private JuzhaiRefreshListView dialogListView;
	private String delDialogUri = "dialog/deleteDialog";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.dialog_title));
		setNavContentView(R.layout.page_dialog_list);
		dialogListView = (JuzhaiRefreshListView) findViewById(R.id.dialog_list_view);

		dialogListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new DialogListGetDataTask(DialogListActivity.this,
						dialogListView).execute(1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new DialogListGetDataTask(DialogListActivity.this,
						dialogListView).execute(dialogListView.getPageAdapter()
						.getPager().getCurrentPage() + 1);
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
						DialogContentListActivity.class);
				intent.putExtra("targetUser", dialog.getTargetUser());
				pushIntent(intent);
			}

		});

		// TODO (review) 长按弹出菜单能否封装一下
		dialogListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> item,
							View view, int position, final long id) {
						new AlertDialog.Builder(DialogListActivity.this)
								.setTitle(R.string.operating)
								.setItems(
										new String[] {
												getResources().getString(
														R.string.dialog_delete),
												getResources().getString(
														R.string.cancel) },
										new OnClickListener() {

											@Override
											public void onClick(
													final DialogInterface dialog,
													int which) {
												dialog.cancel();
												switch (which) {
												case 0:
													final int location = (int) id;
													Dialog d = (Dialog) dialogListView
															.getPageAdapter()
															.getItem(location);
													Map<String, Object> values = new HashMap<String, Object>();
													values.put("dialogId",
															d.getDialogId());

													DialogUtils
															.showConfirmDialog(
																	DialogListActivity.this,
																	new PostProgressTask(
																			DialogListActivity.this,
																			delDialogUri,
																			values,
																			new TaskCallback() {
																				@Override
																				public void successCallback() {
																					dialogListView
																							.getPageAdapter()
																							.deleteData(
																									location);
																				}

																				@Override
																				public String doInBackground() {
																					return null;
																				}
																			}),
																	getResources()
																			.getString(
																					R.string.dialog_delete_confirm_begin)
																			+ d.getTargetUser()
																					.getNickname()
																			+ getResources()
																					.getString(
																							R.string.dialog_delete_confirm_end));
													break;
												}
											}
										}).show();
						return false;
					}
				});

		dialogListView.manualRefresh();
	}

	@Override
	protected void onResume() {
		if (!hasLoad || MainTabActivity.tabBar.getBadgeValue(2) > 0) {
			dialogListView.manualRefresh();
			hasLoad = true;
		}
		super.onResume();
	}
}
