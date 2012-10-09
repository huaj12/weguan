package com.juzhai.android.core.listener;

import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.juzhai.android.core.task.PostTask;
import com.juzhai.android.core.task.TaskSuccessCallBack;

public class SimpleClickListener implements OnClickListener {
	private String uri;
	private Context context;
	private Map<String, String> values;
	private TaskSuccessCallBack callback;
	private boolean defaultStyle = true;

	public SimpleClickListener(String uri, Context context,
			Map<String, String> values, TaskSuccessCallBack callback) {
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
	}

	public SimpleClickListener(String uri, Context context,
			Map<String, String> values, boolean defaultStyle,
			TaskSuccessCallBack callback) {
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
		this.defaultStyle = defaultStyle;
	}

	@Override
	public void onClick(View v) {
		new PostTask(uri, context, values, callback, defaultStyle).execute();
	}
}
