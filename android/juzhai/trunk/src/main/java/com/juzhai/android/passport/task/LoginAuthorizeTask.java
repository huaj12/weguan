package com.juzhai.android.passport.task;

import android.content.Intent;

import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.main.activity.MainTabActivity;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

public class LoginAuthorizeTask extends AbstractAuthorizeTask {

	public LoginAuthorizeTask(BaseActivity baseActivity, long tpId) {
		super(baseActivity, tpId);
	}

	@Override
	protected void successCallback() {
		baseActivity.clearStackAndStartActivity(new Intent(baseActivity,
				MainTabActivity.class));
	}

	@Override
	protected void doAuthorize(long tpId, String[] params)
			throws PassportException {
		IPassportService passportService = new PassportService();
		passportService.tpLogin(baseActivity, tpId, params[0]);
	}

}
