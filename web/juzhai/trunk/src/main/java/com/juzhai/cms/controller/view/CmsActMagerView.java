package com.juzhai.cms.controller.view;

import java.io.Serializable;

import com.juzhai.act.model.Act;

public class CmsActMagerView implements Serializable {
	private static final long serialVersionUID = -1871387836944777684L;

	private Act act;

	private String logoWebPath;

	private String address;

	private String crowd;

	private String category;

	public CmsActMagerView(Act act, String logoWebPath, String address,
			String crowd, String category) {
		super();
		this.act = act;
		this.logoWebPath = logoWebPath;
		this.crowd = crowd;
		this.address = address;
		this.category = category;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public String getLogoWebPath() {
		return logoWebPath;
	}

	public void setLogoWebPath(String logoWebPath) {
		this.logoWebPath = logoWebPath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCrowd() {
		return crowd;
	}

	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
