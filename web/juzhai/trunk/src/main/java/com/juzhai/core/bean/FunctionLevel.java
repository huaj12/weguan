package com.juzhai.core.bean;

//TODO 此类放这个包不合适，今后再说
public enum FunctionLevel {
	SENDSMS(UseLevel.Level1), COMMENT(UseLevel.Level1);

	private UseLevel level;

	private FunctionLevel(UseLevel level) {
		this.level = level;
	}

	public UseLevel getLevel() {
		return level;
	}

	public void setLevel(UseLevel level) {
		this.level = level;
	}

}
