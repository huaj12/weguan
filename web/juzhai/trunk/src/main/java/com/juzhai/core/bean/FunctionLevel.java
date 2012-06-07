package com.juzhai.core.bean;

public enum FunctionLevel {
	SENDSMS(1), COMMENT(1);

	private int level;

	private FunctionLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
