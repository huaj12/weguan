package com.juzhai.android.core.listener;

import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.juzhai.android.core.task.PostProgressTask;
import com.juzhai.android.core.task.TaskCallback;

public class SimpleClickListener implements OnClickListener {
	private String uri;
	private Context context;
	private Map<String, String> values;
	private TaskCallback callback;
	private boolean defaultStyle = true;

	public SimpleClickListener(String uri, Context context,
			Map<String, String> values, TaskCallback callback) {
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
	}

	public SimpleClickListener(String uri, Context context,
			Map<String, String> values, boolean defaultStyle,
			TaskCallback callback) {
		this.uri = uri;
		this.context = context;
		this.values = values;
		this.callback = callback;
		this.defaultStyle = defaultStyle;
	}

	@Override
	public void onClick(View v) {
		new PostProgressTask(context, uri, values, callback, defaultStyle).execute();
	}
}
