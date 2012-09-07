package com.juzhai.core.bean;

//TODO 此类放这个包不合适，今后再说
public enum Function {
	SENDSMS("message", UseLevel.Level1), COMMENT("comment", UseLevel.Level1);

	private UseLevel level;

	private String type;

	private Function(String type, UseLevel level) {
		this.level = level;
		this.type = type;
	}

	public UseLevel getLevel() {
		return level;
	}

	public void setLevel(UseLevel level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
