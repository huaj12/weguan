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
import com.juzhai.android.core.task.PostTask;
import com.juzhai.android.core.task.TaskSuccessCallBack;
import com.juzhai.android.core.utils.DialogUtils;
import com.juzhai.android.core.utils.HttpUtils;
import com.juzhai.android.passport.data.UserCache;

public class SimpleClickListener extends PostTask implements OnClickListener {

	public SimpleClickListener(String uri, Context context,
			Map<String, String> values, TaskSuccessCallBack callback) {
		super(uri, context, values, callback);
	}

	public SimpleClickListener(String uri, Context context,
			Map<String, String> values, boolean defaultStyle,
			TaskSuccessCallBack callback) {
		super(uri, context, values, callback, defaultStyle);

	}

	@Override
	public void onClick(View v) {
		execute();

	}
}
