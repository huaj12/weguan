package com.juzhai.android.common.model;

import com.juzhai.android.core.model.Entity;

public class Profession extends Entity {
	private static final long serialVersionUID = -6304453726896986871L;
	private long id;
	private String name;

	@Override
	public Object getIdentify() {
		return id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
