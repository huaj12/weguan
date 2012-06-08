package com.juzhai.core.bean;

//TODO 此类放这个包不合适，今后再说
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
