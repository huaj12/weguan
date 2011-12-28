package com.juzhai.cms.controller.view;

import com.juzhai.act.model.RawAct;

public class CmsRawActView {
	private RawAct rawAct;
	private String username;

	public CmsRawActView(RawAct rawAct, String username) {
		this.rawAct = rawAct;
		this.username = username;
	}

	public RawAct getRawAct() {
		return rawAct;
	}

	public void setRawAct(RawAct rawAct) {
		this.rawAct = rawAct;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
