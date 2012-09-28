package com.juzhai.android.home.task;

import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.home.bean.ZhaobanOrder;
import com.juzhai.android.passport.model.User;
import com.juzhai.android.post.exception.PostException;
import com.juzhai.android.post.service.IUserPostService;
import com.juzhai.android.post.service.impl.UserPostService;

public class UserPostListGetDataTask extends GetDataTask<UserListResult, User> {

	public UserPostListGetDataTask(JuzhaiRefreshListView refreshListView) {
		super(refreshListView);
	}

	@Override
	protected UserListResult doInBackground(Object... params) {
		Integer gender = (params[0] == null ? null : (Integer) params[0]);
		ZhaobanOrder order = (ZhaobanOrder) params[1];
		int page = (Integer) params[2];
		try {
			IUserPostService userPostService = new UserPostService();
			return userPostService.list(gender, order, page);
		} catch (PostException e) {
			return null;
		}

	}

}
