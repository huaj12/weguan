package com.juzhai.preference.bean;

public enum InputType {
	CHECKBOX(0), RADIO(1), MINANDMAX(2), TEXTAREA(3);

	private int type;

	private InputType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
