package com.juzhai.android.core.listener;

import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.juzhai.android.core.task.PostTask;
import com.juzhai.android.core.task.TaskSuccessCallBack;

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
