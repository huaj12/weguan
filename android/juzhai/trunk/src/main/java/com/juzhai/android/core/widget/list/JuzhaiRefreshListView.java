package com.juzhai.android.core.widget.list;

import android.content.Context;
import android.util.AttributeSet;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.widget.pullrefresh.PullToRefreshListView;

public class JuzhaiRefreshListView extends PullToRefreshListView {

	private PageAdapter<? extends Entity> adapter;

	public JuzhaiRefreshListView(Context context) {
		super(context);
		init();
	}

	public JuzhaiRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public JuzhaiRefreshListView(Context context, Mode mode) {
		super(context, mode);
		init();
	}

	private void init() {
	}

	public void manualRefresh() {
		getRefreshableView().setSelection(0);
		this.setRefreshing();
	}

	public <T extends Entity> void setAdapter(PageAdapter<T> adapter) {
		this.adapter = adapter;
		super.setAdapter(adapter);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> PageAdapter<T> getAdapter() {
		return (PageAdapter<T>) adapter;
	}
}
