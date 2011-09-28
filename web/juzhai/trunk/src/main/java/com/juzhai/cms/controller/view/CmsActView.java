package com.juzhai.cms.controller.view;

import java.io.Serializable;
import java.util.List;

import com.juzhai.act.model.Act;

public class CmsActView implements Serializable {

	private static final long serialVersionUID = -2859544896557453027L;

	private Act act;

	private List<Act> synonymActList;

	public CmsActView(Act act, List<Act> synonymActList) {
		super();
		this.act = act;
		this.synonymActList = synonymActList;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public List<Act> getSynonymActList() {
		return synonymActList;
	}

	public void setSynonymActList(List<Act> synonymActList) {
		this.synonymActList = synonymActList;
	}

	public int getSynonymActSize() {
		return null == getSynonymActList() ? 0 : getSynonymActList().size();
	}
}
