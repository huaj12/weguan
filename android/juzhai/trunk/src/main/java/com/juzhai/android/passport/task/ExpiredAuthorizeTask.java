package com.juzhai.android.passport.task;

import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.listener.TpAuthorizeListener;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

public class ExpiredAuthorizeTask extends AbstractAuthorizeTask {

	public ExpiredAuthorizeTask(BaseActivity baseActivity, long tpId) {
		super(baseActivity, tpId);
	}

	@Override
	protected void successCallback() {
		baseActivity.setResult(TpAuthorizeListener.EXPIRED_SUCCESS_RESULT);
		baseActivity.finish();
	}

	@Override
	protected void doAuthorize(long tpId, String[] params)
			throws PassportException {
		IPassportService passportService = new PassportService();
		passportService.tpExpiredAuthorize(baseActivity, tpId, params[0]);
	}

}
