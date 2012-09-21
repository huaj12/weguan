/**
 * 
 */
package com.juzhai.android.idea.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.TextTruncateUtil;
import com.juzhai.android.core.utils.Validation;
import com.juzhai.android.core.widget.navigation.app.NavigationActivity;
import com.juzhai.android.idea.adapter.IdeaUserAdapter;
import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.model.IdeaUserAndPager;
import com.juzhai.android.idea.model.IdeaUserResult;
import com.juzhai.android.idea.service.IIdeaService;
import com.juzhai.android.idea.service.impl.IdeaService;

/**
 * @author kooks
 * 
 */
public class IdeaUsersActivity extends NavigationActivity {
	private ProgressBar bar;
	private int page = 1;
	private ListView listView;
	private Idea idea;
	private BaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		idea = (Idea) getIntent().getSerializableExtra("idea");
		if (idea == null) {
			popIntent();
		}
		// 内容视图
		setNavContentView(R.layout.page_idea_users);
		getNavigationBar().setBarTitle(
				getResources().getString(R.string.idea_users_title));
		Button refeshBtn = (Button) getLayoutInflater().inflate(
				R.layout.button_refresh, null);
		refeshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				refreshList();
			}
		});
		getNavigationBar().setRightView(refeshBtn);
		bar = (ProgressBar) findViewById(R.id.pro_bar);
		listView = (ListView) findViewById(R.id.idea_users_listview);
		TextView ideaContent = (TextView) findViewById(R.id.idea_users_content);
		ideaContent.setText(TextTruncateUtil.truncate(idea.getContent(),
				Validation.IDEA_USER_CONTENT_MAX_LENGTH, "..."));
		refreshList();
	}

	private void refreshList() {
		new AsyncTask<String, Integer, IdeaUserAndPager>() {

			@Override
			protected IdeaUserAndPager doInBackground(String... params) {
				IIdeaService ideaService = new IdeaService();
				IdeaUserResult result = null;
				try {
					result = ideaService.listIdeaUser(idea.getIdeaId(), page);
				} catch (IdeaException e) {
					DialogUtils.showToastText(IdeaUsersActivity.this,
							e.getMessageId());
					return null;
				}
				if (result != null && !result.getSuccess()) {
					DialogUtils.showToastText(IdeaUsersActivity.this,
							result.getErrorInfo());
					return null;
				}
				return result.getResult();
			}

			@Override
			protected void onPostExecute(IdeaUserAndPager result) {
				bar.setVisibility(View.GONE);
				if (result != null) {
					listView.setVisibility(View.VISIBLE);
					adapter = new IdeaUserAdapter(result.getList(),
							IdeaUsersActivity.this, LAYOUT_INFLATER_SERVICE);
					listView.setAdapter(adapter);
				}
			}

			@Override
			protected void onPreExecute() {
				bar.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
			}

		}.execute();
	}

}
