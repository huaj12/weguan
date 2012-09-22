package com.juzhai.android.core.listener;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;

//TODO (review) BaseListener这个名字取的太大
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
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				ResponseEntity<StringResult> responseEntity = null;
				try {
					responseEntity = HttpUtils.post(uri, values,
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
					// TODO (review) 成功提示如果需要自定义呢？
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
