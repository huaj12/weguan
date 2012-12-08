/**
 * 
 */
package com.juzhai.android.idea.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.widget.button.GenderButton;
import com.juzhai.android.core.widget.button.GenderButton.GenderButtonCallback;
import com.juzhai.android.core.widget.button.SegmentedButton;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase;
import com.juzhai.android.core.widget.list.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaUserListAdapter;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.task.IdeaUserListGetDataTask;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.model.User;

/**
 * @author kooks
 * 
 */
public class IdeaUsersActivity extends NavigationActivity {
	private JuzhaiRefreshListView listView;
	private long cityId;
	private Integer gender = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Idea idea = (Idea) getIntent().getSerializableExtra("idea");
		final User user = UserCacheManager.getUserCache(IdeaUsersActivity.this)
				.getUserInfo();
		cityId = user.getCityId();
		if (idea == null) {
			popIntent();
		}
		// 内容视图
		setNavContentView(R.layout.page_idea_users);
		SegmentedButton segmentedButton = new SegmentedButton(this,
				getResources().getStringArray(R.array.idea_user_head_tab_item),
				80, 32);
		segmentedButton
				.setOnClickListener(new SegmentedButton.OnClickListener() {
					@Override
					public void onClick(Button button, int which) {
						switch (which) {
						case 0:
							cityId = user.getCityId();
							break;
						case 1:
							cityId = 0;
							break;
						}
						listView.manualRefresh();

					}
				});
		getNavigationBar().setBarTitleView(segmentedButton);
		getNavigationBar().setRightView(
				new GenderButton(IdeaUsersActivity.this,
						new GenderButtonCallback() {

							@Override
							public void onClickCallback(Integer selectGender) {
								if (gender == null && selectGender == null) {
									return;
								} else if (gender != null
										&& selectGender != null) {
									if (selectGender.intValue() == gender) {
										return;
									}
								}
								gender = selectGender;
								listView.manualRefresh();
							}
						}));
		TextView ideaContent = (TextView) findViewById(R.id.idea_users_content);
		TextView ideaContentEnd = (TextView) findViewById(R.id.idea_users_content_end_view);
		final TextView ideaContentBegin = (TextView) findViewById(R.id.idea_users_content_begin_view);
		ideaContent.setText(idea.getContent());
		ideaContentBegin.setText(getResources().getString(
				R.string.idea_users_content_begin_text, 0));
		ideaContentEnd.setText(getResources().getString(
				R.string.idea_users_content_end_text, idea.getUseCount()));
		listView = (JuzhaiRefreshListView) findViewById(R.id.idea_users_list_view);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullDownToRefresh(refreshView);
				new IdeaUserListGetDataTask(IdeaUsersActivity.this, listView,
						ideaContentBegin).execute(idea.getIdeaId(), cityId,
						gender, 1);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				super.onPullUpToRefresh(refreshView);
				new IdeaUserListGetDataTask(IdeaUsersActivity.this, listView,
						ideaContentBegin).execute(idea.getIdeaId(), cityId,
						gender, listView.getPageAdapter().getPager()
								.getCurrentPage() + 1);
			}
		});
		listView.setAdapter(new IdeaUserListAdapter(IdeaUsersActivity.this));
		listView.manualRefresh();
	}
}
