package com.juzhai.android.post.task;

import android.content.Context;

import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.service.IUserPostService;
import com.juzhai.android.post.service.impl.UserPostService;

public class ResponseListGetDataTask extends GetDataTask<UserListResult, User> {

	public ResponseListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected UserListResult doInBackground(Object... params) {
		long postid = (Long) params[0];
		int page = (Integer) params[1];
		try {
			IUserPostService userPostService = new UserPostService();
			return userPostService.respUsers(context, postid, page);
		} catch (PostException e) {
			return null;
		}
	}

}
