package com.juzhai.passport.bean;

public enum TodayVisit {

	Home(1);

	private int type;

	private TodayVisit(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
