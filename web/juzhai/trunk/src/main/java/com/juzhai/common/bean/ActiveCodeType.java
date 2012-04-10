package com.juzhai.common.bean;

public enum ActiveCodeType {

	ACTIVE_EMAIL(1), RESET_PWD(2);

	private int type;

	private ActiveCodeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
