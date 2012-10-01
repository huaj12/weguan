package com.juzhai.android.passport.task;

import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.passport.exception.PassportException;
import com.juzhai.android.passport.listener.TpAuthorizeListener;
import com.juzhai.android.passport.service.IPassportService;
import com.juzhai.android.passport.service.impl.PassportService;

public class BindAuthorizeTask extends AbstractAuthorizeTask {

	public BindAuthorizeTask(BaseActivity baseActivity, long tpId) {
		super(baseActivity, tpId);
	}

	@Override
	protected void successCallback() {
		baseActivity.setResult(TpAuthorizeListener.BIND_SUCCESS_RESULT);
		baseActivity.finish();
	}

	@Override
	protected void doAuthorize(long tpId, String[] params)
			throws PassportException {
		IPassportService passportService = new PassportService();
		passportService.tpBind(baseActivity, tpId, params[0]);
	}
}
