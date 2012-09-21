package com.juzhai.android.core.widget.list;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.model.Pager;

public abstract class PageAdapter<T extends Entity> extends BaseAdapter {

	protected PageAdapterData<T> data = new PageAdapterData<T>();
	protected Context mContext;
	protected LayoutInflater inflater;

	public PageAdapter(Context mContext) {
		this.mContext = mContext;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (data == null || data.getDatas() == null) {
			return 0;
		} else {
			return data.getDatas().size();
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		if (data == null || data.getDatas() == null) {
			return null;
		} else {
			return data.getDatas().get(position);
		}
	}

	public void pushDatas(List<T> datas) {
		data.addAll(datas);
	}

	public void setDatas(List<T> datas) {
		data.clearAndAddAll(datas);
	}

	public void setPager(Pager pager) {
		data.setPager(pager);
	}

	public Pager getPager() {
		return data.getPager();
	}
}