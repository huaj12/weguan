package com.juzhai.act.bean;

public enum DatingResponse {

	ACCEPT(1);

	private int value;

	private DatingResponse(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
