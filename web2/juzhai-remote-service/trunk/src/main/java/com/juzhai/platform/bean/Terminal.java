package com.juzhai.platform.bean;

public enum Terminal {
	PC(""), MOBILE("mobile");

	private String type;

	private Terminal(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
