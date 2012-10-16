package com.juzhai.android.home.task;

import android.content.Context;
import android.widget.TextView;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.PostListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.model.Post;
import com.juzhai.android.post.service.IUserPostService;
import com.juzhai.android.post.service.impl.UserPostService;

public class MyPostsListGetDataTask extends GetDataTask<PostListResult, Post> {
	private TextView view;

	public MyPostsListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView, TextView view) {
		super(context, refreshListView);
		this.view = view;
	}

	public MyPostsListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected PostListResult doInBackground(Object... params) {
		long uid = (Long) params[0];
		int page = (Integer) params[1];
		try {
			IUserPostService userPostService = new UserPostService();
			return userPostService.listPosts(context, uid, page);
		} catch (PostException e) {
			return null;
		}
	}

	@Override
	protected void onPostExecute(PostListResult result) {
		super.onPostExecute(result);
		if (view != null && context != null) {
			int count = refreshListView.getPageAdapter().getPager()
					.getTotalResults() == null ? 0 : refreshListView
					.getPageAdapter().getPager().getTotalResults();
			view.setText(context.getResources().getString(
					R.string.user_home_post_count_begin)
					+ count
					+ context.getResources().getString(
							R.string.user_home_post_count_end));
		}
	}

}
