package com.easylife.weather.common.service;

public enum SharedApp {

	WEIXIN("com.tencent.mm"), MMS("com.android.mms");

	private String packageName;

	private String name;

	private SharedApp(String packageName) {
		this.packageName = packageName;
	}

	private SharedApp(String packageName, String name) {
		this.packageName = packageName;
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getName() {
		return name;
	}

	public static SharedApp getSharedAppByPackageName(String packageName) {
		for (SharedApp sharedApp : SharedApp.values()) {
			if (sharedApp.getPackageName().equals(packageName)) {
				return sharedApp;
			}
		}
		return null;
	}
}
