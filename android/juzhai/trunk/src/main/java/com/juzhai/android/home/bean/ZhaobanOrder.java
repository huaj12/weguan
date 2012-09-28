package com.juzhai.android.home.bean;

public enum ZhaobanOrder {

	ONLINE("online"), NEW("new");

	private String name;

	private ZhaobanOrder(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
