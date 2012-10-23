package com.juzhai.android.common.service;

public enum SharedApp {

	WEIBO("com.sina.weibo"), WEIXIN("com.tencent.mm"), RENREN(
			"com.renren.mobile.android"), QZONE("com.qzone",
			"com.qzone.QZonePublishMoodActivity"), TWBLOG("com.tencent.WBlog"), MMS(
			"com.android.mms"), EMAIL("com.android.email");

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
