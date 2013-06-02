package com.easylife.weather.core.task;

import org.springframework.util.StringUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.easylife.weather.R;

public class ProgressTask extends AsyncTask<Void, Void, String> {
	protected Context context;
	protected ProgressDialog progressDialog;
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
		if (StringUtils.hasText(errorInfo)) {
			Toast.makeText(context, errorInfo, Toast.LENGTH_SHORT).show();
		} else {
			if (defaultStyle) {
				Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT)
						.show();
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
