package com.juzhai.android.core.listener;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;

public class BaseListener implements OnClickListener {
	private String uri;
	private ProgressDialog progressDialog;
	private Context context;
	private Map<String, String> values;
	private ListenerSuccessCallBack callback;

	public BaseListener(String uri, Context context,
			Map<String, String> values, ListenerSuccessCallBack callback) {
		super();
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
	}

	@Override
	public void onClick(View v) {
		final Button btn = (Button) v;
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				ResponseEntity<Result> responseEntity = null;
				try {
					responseEntity = HttpUtils.post(uri, values,
							UserCache.getUserStatus(), Result.class);
				} catch (Exception e) {
					return context.getResources().getString(
							R.string.system_internet_erorr);
				}
				Result results = responseEntity.getBody();
				if (!results.getSuccess()) {
					return results.getErrorInfo();
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
					DialogUtils.showToastText(context, R.string.success);
					callback.callback();
				}
			}

			@Override
			protected void onPreExecute() {
				if (progressDialog != null) {
					progressDialog.show();
				} else {
					progressDialog = ProgressDialog.show(
							context,
							context.getResources().getString(R.string.sending),
							context.getResources().getString(
									R.string.please_wait), true, false);
				}
			}
		}.execute();

	}
}
