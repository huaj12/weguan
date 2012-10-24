package com.juzhai.android.passport.task;

import android.content.Intent;

import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.main.activity.UserGuideActivity;
import com.juzhai.android.passport.data.UserCache;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

public class LoginAuthorizeTask extends AbstractAuthorizeTask {

	public LoginAuthorizeTask(BaseActivity baseActivity, long tpId) {
		super(baseActivity, tpId);
	}

	@Override
	protected void successCallback() {
		if (UserCache.getUserInfo().isHasGuided()) {
			baseActivity.clearStackAndStartActivity(new Intent(baseActivity,
					MainTabActivity.class));
		} else {
			baseActivity.clearStackAndStartActivity(new Intent(baseActivity,
					UserGuideActivity.class));
		}
	}

	@Override
	protected void doAuthorize(long tpId, String[] params)
			throws PassportException {
		IPassportService passportService = new PassportService();
		passportService.tpLogin(baseActivity, tpId, params[0]);
	}

}
