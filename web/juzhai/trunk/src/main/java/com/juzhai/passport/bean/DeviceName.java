package com.juzhai.passport.bean;

public enum DeviceName {
	BROWSER("browser"), IPHONE("iphone");

	private String name;

	private DeviceName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
