package com.juzhai.android.core.widget.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.model.Pager;

public class PageAdapterData<T extends Entity> {

	private List<T> datas = new ArrayList<T>();

	private Pager pager;

	private Set<Object> identifySet = new HashSet<Object>();

	public void addData(T data) {
		if (null == data
				|| (data.getIdentify() != null && identifySet.contains(data
						.getIdentify()))) {
			return;
		}
		if (null != data.getIdentify()) {
			identifySet.add(data.getIdentify());
		}
		this.datas.add(data);
	}

	public void addAll(List<T> datas) {
		for (T data : datas) {
			addData(data);
		}
	}

	public void refreshIdentifySet() {
		identifySet.clear();
		for (T data : datas) {
			if (data.getIdentify() != null) {
				identifySet.add(data.getIdentify());
			}
		}
	}

	public void clearAndAddAll(List<T> datas) {
		this.datas.clear();
		identifySet.clear();
		addAll(datas);
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<T> getDatas() {
		return datas;
	}

	public int getCount() {
		return datas.size();
	}

	public void replaceData(int location, T data) {
		T preData = this.datas.get(location);
		if (null == data
				|| (data.getIdentify() != null
						&& !data.getIdentify().equals(preData.getIdentify()) && identifySet
							.contains(data.getIdentify()))) {
			return;
		}
		identifySet.remove(preData.getIdentify());
		if (null != data.getIdentify()) {
			identifySet.add(data.getIdentify());
		}
		this.datas.set(location, data);
	}
}
