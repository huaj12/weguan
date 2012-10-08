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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.juzhai.android.R;
import com.juzhai.android.core.task.PostTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.dialog.activity.DialogContentListActivity;
import com.juzhai.android.home.adapter.InterestUserListAdapter;
import com.juzhai.android.home.task.InterestMeListGetDataTask;
import com.juzhai.android.passport.model.User;

/**
 * @author kooks
 * 
 */
public class InterestMeActivity extends NavigationActivity {
	private String interestUri = "home/interest";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_interest);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.interest_me_title));
		// TODO (review) 自己看什么问题？
		final JuzhaiRefreshListView interestListView = (JuzhaiRefreshListView) findViewById(R.id.interest_list_view);
		interestListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullDownToRefresh(refreshView);
						new InterestMeListGetDataTask(InterestMeActivity.this,
								interestListView).execute(1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullUpToRefresh(refreshView);
						new InterestMeListGetDataTask(InterestMeActivity.this,
								interestListView)
								.execute(interestListView.getPageAdapter()
										.getPager().getCurrentPage() + 1);
					}
				});
		interestListView.setAdapter(new InterestUserListAdapter(
				InterestMeActivity.this));
		interestListView.manualRefresh();
		interestListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> item,
							View view, int position, final long id) {
						new AlertDialog.Builder(InterestMeActivity.this)
								.setTitle(R.string.operating)
								.setItems(
										new String[] {
												getResources().getString(
														R.string.interest),
												getResources()
														.getString(
																R.string.private_letter),
												getResources().getString(
														R.string.cancel) },
										new OnClickListener() {

											@Override
											public void onClick(
													final DialogInterface dialog,
													int which) {
												dialog.cancel();
												final int location = (int) id;
												User user = (User) interestListView
														.getPageAdapter()
														.getItem(location);
												switch (which) {
												case 0:
													Map<String, String> values = new HashMap<String, String>();
													values.put("uid", String
															.valueOf(user
																	.getUid()));
													new PostTask(
															interestUri,
															InterestMeActivity.this,
															values, null)
															.execute();
													break;
												case 1:
													Intent intent = new Intent(
															InterestMeActivity.this,
															DialogContentListActivity.class);
													intent.putExtra(
															"targetUser", user);
													pushIntent(intent);
													break;
												}

											}
										}).show();
						return false;
					}
				});

		interestListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				int location = (int) id;
				User user = (User) interestListView.getPageAdapter().getItem(
						location);
				Intent intent = new Intent(InterestMeActivity.this,
						UserHomeActivity.class);
				intent.putExtra("targetUser", user);
				pushIntent(intent);
			}
		});

	}

}
