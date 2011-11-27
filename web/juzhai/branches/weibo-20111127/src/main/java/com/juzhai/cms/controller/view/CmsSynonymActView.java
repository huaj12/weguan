package com.juzhai.cms.controller.view;

import com.juzhai.act.model.SynonymAct;

public class CmsSynonymActView {
	private SynonymAct synonymAct;

	private String actName;

	public CmsSynonymActView(String actName, SynonymAct synonymAct) {
		this.synonymAct = synonymAct;
		this.actName = actName;
	}

	public SynonymAct getSynonymAct() {
		return synonymAct;
	}

	public void setSynonymAct(SynonymAct synonymAct) {
		this.synonymAct = synonymAct;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
}
