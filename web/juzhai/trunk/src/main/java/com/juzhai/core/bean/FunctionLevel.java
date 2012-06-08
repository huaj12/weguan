package com.juzhai.core.bean;

//TODO 此类放这个包不合适，今后再说
public enum FunctionLevel {
	//TODO (review) 有了UseLevel,这里为什么还要用int呢？
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
