package com.juzhai.platform.bean;

public enum SynchronizeQqTemplate {
	SYNCHRONIZE_TITLE("qq.register.share.title"), SYNCHRONIZE_LINK(
			"qq.register.share.link"), SYNCHRONIZE_IMAGE(
			"qq.register.share.imgUrl");

	private String name;

	private SynchronizeQqTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
