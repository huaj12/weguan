package com.juzhai.post.bean;

public enum SynchronizeWeiboTemplate {
	SYNCHRONIZE_WEIBO_TEXT("synchronize.weibo.text"),
	SYNCHRONIZE_WEIBO_TIME("synchronize.weibo.time"),
	SYNCHRONIZE_WEIBO_ADDRESS("synchronize.weibo.address");

	private String name;

	private SynchronizeWeiboTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
