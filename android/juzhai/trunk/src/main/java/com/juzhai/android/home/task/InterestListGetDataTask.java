package com.juzhai.android.home.task;

import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.home.exception.HomeException;
import com.juzhai.android.home.service.IInterestUserService;
import com.juzhai.android.home.service.impl.InterestUserService;
import com.juzhai.android.passport.model.User;

public class InterestListGetDataTask extends GetDataTask<UserListResult, User> {

	public InterestListGetDataTask(JuzhaiRefreshListView refreshListView) {
		super(refreshListView);
	}

	@Override
	protected UserListResult doInBackground(Object... params) {
		int page = (Integer) params[0];
		try {
			IInterestUserService interestUserService = new InterestUserService();
			return interestUserService.interestList(page);
		} catch (HomeException e) {
			return null;
		}
	}

}
