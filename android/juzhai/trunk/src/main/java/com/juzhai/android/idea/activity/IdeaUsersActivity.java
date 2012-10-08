/**
 * 
 */
package com.juzhai.android.idea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.home.activity.UserHomeActivity;
import com.juzhai.android.idea.adapter.IdeaUserListAdapter;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.idea.task.IdeaUserListGetDataTask;

/**
 * @author kooks
 * 
 */
public class IdeaUsersActivity extends NavigationActivity {
	private JuzhaiRefreshListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Idea idea = (Idea) getIntent().getSerializableExtra("idea");
		if (idea == null) {
			popIntent();
		}
		// 内容视图
		setNavContentView(R.layout.page_idea_users);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.idea_users_title));
		TextView ideaContent = (TextView) findViewById(R.id.idea_users_content);
		ideaContent.setText(TextTruncateUtil.truncate(idea.getContent(), 30,
				"..."));
		listView = (JuzhaiRefreshListView) findViewById(R.id.idea_users_list_view);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new IdeaUserListGetDataTask(IdeaUsersActivity.this, listView)
						.execute(idea.getIdeaId(), 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new IdeaUserListGetDataTask(IdeaUsersActivity.this, listView)
						.execute(idea.getIdeaId(), listView.getPageAdapter()
								.getPager().getCurrentPage() + 1);
			}
		});
		listView.setAdapter(new IdeaUserListAdapter(IdeaUsersActivity.this));
		listView.manualRefresh();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> item, View view, int arg2,
					long id) {
				int position = (int) id;
				IdeaUser ideaUser = (IdeaUser) listView.getPageAdapter()
						.getItem(position);
				Intent intent = new Intent(IdeaUsersActivity.this,
						UserHomeActivity.class);
				intent.putExtra("targetUser", ideaUser.getUserView());
				pushIntent(intent);

			}
		});
	}
}
