package com.juzhai.android.passport.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.juzhai.android.R;
import com.juzhai.android.core.activity.BaseActivity;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.passport.exception.PassportException;

public abstract class AbstractAuthorizeTask extends
		AsyncTask<String, Integer, String> {

	private ProgressDialog progressDialog;
	protected BaseActivity baseActivity;
	protected long tpId;

	public AbstractAuthorizeTask(BaseActivity baseActivity, long tpId) {
		super();
		this.baseActivity = baseActivity;
		this.tpId = tpId;
	}

	@Override
	protected String doInBackground(String... params) {
		// 登录
		try {
			doAuthorize(tpId, params);
			return null;
		} catch (PassportException e) {
			return e.getMessage();
		}
	}

	@Override
	protected void onPreExecute() {
		if (progressDialog != null) {
			progressDialog.show();
		} else {
			progressDialog = ProgressDialog
					.show(baseActivity,
							baseActivity.getResources().getString(
									R.string.tip_loging),
							baseActivity.getResources().getString(
									R.string.please_wait), true, false);
		}
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String errorInfo) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		if (errorInfo != null && !errorInfo.equals("")) {
			DialogUtils.showErrorDialog(baseActivity, errorInfo);
			// DialogUtils.showToastText(baseActivity, errorInfo);
		} else {
			// success
			successCallback();
		}
		super.onPostExecute(errorInfo);
	}

	protected abstract void successCallback();

	protected abstract void doAuthorize(long tpId, String[] params)
			throws PassportException;
}
