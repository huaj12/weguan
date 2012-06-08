package com.juzhai.core.bean;

public enum UseLevel {
	Level0(0), Level1(1);

	private int level;

	private UseLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
