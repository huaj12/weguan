package com.juzhai.android.idea.model;

import java.util.List;

import com.juzhai.android.core.model.PagerManager;

public class IdeaListAndPager {
	private PagerManager pager;
	private List<Idea> list;

	public PagerManager getPager() {
		return pager;
	}

	public void setPager(PagerManager pager) {
		this.pager = pager;
	}

	public List<Idea> getList() {
		return list;
	}

	public void setList(List<Idea> list) {
		this.list = list;
	}

}
