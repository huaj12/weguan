package com.juzhai.android.core.task;

import org.apache.commons.lang.StringUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.juzhai.android.R;
import com.juzhai.android.core.utils.DialogUtils;

public class ProgressTask extends AsyncTask<Void, Void, String> {
	protected Context context;
	private ProgressDialog progressDialog;
	private TaskCallback callback;
	private boolean defaultStyle = true;

	public ProgressTask(Context context, TaskCallback callback) {
		super();
		this.context = context;
		this.callback = callback;
	}

	public ProgressTask(Context context, TaskCallback callback,
			boolean defaultStyle) {
		super();
		this.context = context;
		this.callback = callback;
		this.defaultStyle = defaultStyle;
	}

	@Override
	protected String doInBackground(Void... params) {
		if (null != callback) {
			return callback.doInBackground();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String errorInfo) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		if (StringUtils.isNotEmpty(errorInfo)) {
			DialogUtils.showToastText(context, errorInfo);
		} else {
			if (defaultStyle) {
				DialogUtils.showToastText(context, R.string.success);
			}
			if (callback != null) {
				callback.successCallback();
			}
		}
	}

	@Override
	protected void onPreExecute() {
		if (progressDialog != null) {
			progressDialog.show();
		} else {
			progressDialog = ProgressDialog.show(context, context
					.getResources().getString(R.string.sending), context
					.getResources().getString(R.string.please_wait), true,
					false);
		}
		super.onPreExecute();
	}
}
