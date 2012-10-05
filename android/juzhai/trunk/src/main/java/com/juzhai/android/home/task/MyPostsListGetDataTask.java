package com.juzhai.android.home.task;

import com.juzhai.android.core.model.Result.PostListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.service.IUserPostService;
import com.juzhai.android.post.service.impl.UserPostService;

public class MyPostsListGetDataTask extends GetDataTask<PostListResult, Post> {

	public MyPostsListGetDataTask(JuzhaiRefreshListView refreshListView) {
		super(refreshListView);
	}

	@Override
	protected PostListResult doInBackground(Object... params) {
		long uid = (Long) params[0];
		int page = (Integer) params[1];
		try {
			IUserPostService userPostService = new UserPostService();
			return userPostService.listPosts(uid, page);
		} catch (PostException e) {
			return null;
		}
	}

}
