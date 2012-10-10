package com.juzhai.android.core.task;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import android.content.Context;

import com.juzhai.android.R;
import com.juzhai.android.core.model.Result.StringResult;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;

public class PostProgressTask extends ProgressTask {

	public PostProgressTask(final Context context, final String uri,
			final Map<String, String> values, final TaskCallback callback) {
		this(context, uri, values, callback, false);
	}

	public PostProgressTask(final Context context, final String uri,
			final Map<String, String> values, final TaskCallback callback,
			boolean defaultStyle) {
		super(context, new TaskCallback() {

			@Override
			public void successCallback() {
				callback.successCallback();
			}

			@Override
			public String doInBackground() {
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
		}, defaultStyle);
	}
}
