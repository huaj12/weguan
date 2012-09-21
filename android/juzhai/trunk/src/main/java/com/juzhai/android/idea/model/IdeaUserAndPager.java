package com.juzhai.android.idea.model;

import java.util.List;

import com.juzhai.android.core.pager.Pager;

public class IdeaUserAndPager {
	private Pager pager;
	private List<IdeaUser> list;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public List<IdeaUser> getList() {
		return list;
	}

	public void setList(List<IdeaUser> list) {
		this.list = list;
	}

}
