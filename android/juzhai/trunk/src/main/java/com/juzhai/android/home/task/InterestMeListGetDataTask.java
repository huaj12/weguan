package com.juzhai.android.home.task;

import android.content.Context;

import com.juzhai.android.core.model.Result.UserListResult;
import com.juzhai.android.core.widget.list.GetDataTask;
import com.juzhai.android.core.widget.list.JuzhaiRefreshListView;
import com.juzhai.android.home.exception.HomeException;
import com.juzhai.android.home.service.IInterestUserService;
import com.juzhai.android.home.service.impl.InterestUserService;
import com.juzhai.android.passport.model.User;

public class InterestMeListGetDataTask extends
		GetDataTask<UserListResult, User> {

	public InterestMeListGetDataTask(Context context,
			JuzhaiRefreshListView refreshListView) {
		super(context, refreshListView);
	}

	@Override
	protected UserListResult doInBackground(Object... params) {
		int page = (Integer) params[0];
		try {
			IInterestUserService interestUserService = new InterestUserService();
			return interestUserService.interestMeList(context, page);
		} catch (HomeException e) {
			return null;
		}
	}

}
