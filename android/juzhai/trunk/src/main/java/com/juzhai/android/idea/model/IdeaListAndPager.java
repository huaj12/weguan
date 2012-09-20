package com.juzhai.android.idea.model;

import java.util.List;

import com.juzhai.android.core.pager.Pager;

public class IdeaListAndPager {
	private Pager pager;
	private List<Idea> list;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<Idea> getList() {
		return list;
	}

	public void setList(List<Idea> list) {
		this.list = list;
	}

}
