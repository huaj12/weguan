package com.juzhai.android.core.task;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;

public class PostTask extends AsyncTask<Void, Void, String> {
	private String uri;
	private ProgressDialog progressDialog;
	private Context context;
	private Map<String, String> values;
	private TaskSuccessCallBack callback;
	private boolean defaultStyle = true;

	public PostTask(String uri, Context context, Map<String, String> values,
			TaskSuccessCallBack callback) {
		super();
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
	}

	public PostTask(String uri, Context context, Map<String, String> values,
			TaskSuccessCallBack callback, boolean defaultStyle) {
		super();
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
		this.defaultStyle = defaultStyle;
	}

	@Override
	protected String doInBackground(Void... params) {
		ResponseEntity<StringResult> responseEntity = null;
		try {
			responseEntity = HttpUtils.post(context, uri, values,
					UserCache.getUserStatus(), StringResult.class);
		} catch (Exception e) {
			return context.getResources().getString(
					R.string.system_internet_erorr);
		}
		StringResult result = responseEntity.getBody();
		if (!result.getSuccess()) {
			return result.getErrorInfo();
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
				callback.callback();
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
