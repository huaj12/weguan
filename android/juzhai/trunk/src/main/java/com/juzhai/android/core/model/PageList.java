package com.juzhai.android.core.model;

import java.util.List;

public class PageList<T extends Entity> {

	private Pager pager;

	private List<T> list;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
