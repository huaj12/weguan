package com.juzhai.preference.bean;

public enum PreferenceType {
	SHOW(0), FILTER(1), MATCH(2);

	private int type;

	private PreferenceType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
