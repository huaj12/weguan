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
import com.juzhai.android.home.adapter.InterestUserListAdapter;
import com.juzhai.android.home.task.InterestMeListGetDataTask;
import com.juzhai.android.passport.model.User;

/**
 * @author kooks
 * 
 */
public class InterestMeActivity extends NavigationActivity {
	// private String interestUri = "home/interest";
	// private String unInterestUri = "home/removeInterest";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNavContentView(R.layout.page_interest);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.interest_me_title));
		// TODO (done) 自己看什么问题？
		final JuzhaiRefreshListView interestMeListView = (JuzhaiRefreshListView) findViewById(R.id.interest_list_view);
		interestMeListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullDownToRefresh(refreshView);
						new InterestMeListGetDataTask(InterestMeActivity.this,
								interestMeListView).execute(1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						super.onPullUpToRefresh(refreshView);
						new InterestMeListGetDataTask(InterestMeActivity.this,
								interestMeListView)
								.execute(interestMeListView.getPageAdapter()
										.getPager().getCurrentPage() + 1);
					}
				});
		interestMeListView.setAdapter(new InterestUserListAdapter(
				InterestMeActivity.this));
		interestMeListView.manualRefresh();

		// interestListView
		// .setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> item,
		// View view, int position, final long id) {
		// final int location = (int) id;
		// final User user = (User) interestListView
		// .getPageAdapter().getItem(location);
		// String title = null;
		// if (user.isHasInterest()) {
		// title = getResources().getString(
		// R.string.un_interest);
		// } else {
		// title = getResources().getString(R.string.interest);
		// }
		// new AlertDialog.Builder(InterestMeActivity.this)
		// .setTitle(R.string.operating)
		// .setItems(
		// new String[] {
		// title,
		// getResources()
		// .getString(
		// R.string.private_letter),
		// getResources().getString(
		// R.string.cancel) },
		// new OnClickListener() {
		//
		// @Override
		// public void onClick(
		// final DialogInterface dialog,
		// int which) {
		// dialog.cancel();
		// switch (which) {
		// case 0:
		// String url = null;
		// if (user.isHasInterest()) {
		// url = unInterestUri;
		// } else {
		// url = interestUri;
		// }
		// Map<String, String> values = new HashMap<String, String>();
		// values.put("uid", String
		// .valueOf(user
		// .getUid()));
		// new PostTask(
		// url,
		// InterestMeActivity.this,
		// values,
		// new TaskSuccessCallBack() {
		//
		// @Override
		// public void callback() {
		// user.setHasInterest(!user
		// .isHasInterest());
		// interestListView
		// .getPageAdapter()
		// .replaceData(
		// location,
		// user);
		// }
		// }).execute();
		// break;
		// case 1:
		// Intent intent = new Intent(
		// InterestMeActivity.this,
		// DialogContentListActivity.class);
		// intent.putExtra(
		// "targetUser", user);
		// pushIntent(intent);
		// break;
		// }
		//
		// }
		// }).show();
		// return false;
		// }
		// });

		interestMeListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				int location = (int) id;
				User user = (User) interestMeListView.getPageAdapter().getItem(
						location);
				Intent intent = new Intent(InterestMeActivity.this,
						UserHomeActivity.class);
				intent.putExtra("targetUser", user);
				pushIntent(intent);
			}
		});

	}
}
