package com.juzhai.post.bean;

public enum SynchronizeWeiboTemplate {
	SYNCHRONIZE_WEIBO_TEXT("synchronize.weibo.text");

	private String name;

	private SynchronizeWeiboTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
