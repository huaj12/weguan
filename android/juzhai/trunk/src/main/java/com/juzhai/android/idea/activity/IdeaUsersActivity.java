/**
 * 
 */
package com.juzhai.android.idea.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaUserListAdapter;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.task.IdeaUserListGetDataTask;

/**
 * @author kooks
 * 
 */
public class IdeaUsersActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Idea idea = (Idea) getIntent().getSerializableExtra("idea");
		if (idea == null) {
			popIntent();
		}
		// 内容视图
		setNavContentView(R.layout.page_idea_users);
		final JuzhaiRefreshListView listView = (JuzhaiRefreshListView) findViewById(R.id.idea_users_list_view);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.idea_users_title));
		Button refeshBtn = (Button) getLayoutInflater().inflate(
				R.layout.button_refresh, null);
		getNavigationBar().setRightView(refeshBtn);
		refeshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.manualRefresh();
			}
		});
		TextView ideaContent = (TextView) findViewById(R.id.idea_users_content);
		ideaContent.setText(TextTruncateUtil.truncate(idea.getContent(), 30,
				"..."));

		// list
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new IdeaUserListGetDataTask(listView).execute(idea.getIdeaId(),
						1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new IdeaUserListGetDataTask(listView).execute(
						idea.getIdeaId(),
						listView.getPageAdapter().getPager().getCurrentPage() + 1);
			}
		});
		listView.setAdapter(new IdeaUserListAdapter(IdeaUsersActivity.this));

		listView.manualRefresh();
	}
}
