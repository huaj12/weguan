package com.juzhai.post.bean;

public enum SynchronizeWeiboTemplate {
	SYNCHRONIZE_TEXT("synchronize.text"), SYNCHRONIZE_TIME("synchronize.time"), SYNCHRONIZE_ADDRESS(
			"synchronize.address"), SYNCHRONIZE_TITLE("synchronize.title"), SYNCHRONIZE_LINK(
			"synchronize.link");

	private String name;

	private SynchronizeWeiboTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
