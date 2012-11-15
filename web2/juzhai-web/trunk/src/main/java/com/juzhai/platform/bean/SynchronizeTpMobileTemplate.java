package com.juzhai.platform.bean;

public enum SynchronizeTpMobileTemplate {
	SYNCHRONIZE_TITLE("mobile.tp.register.share.title"), SYNCHRONIZE_TEXT(
			"mobile.tp.register.share.text"), SYNCHRONIZE_LINK(
			"mobile.tp.register.share.link"), SYNCHRONIZE_IMAGE(
			"mobile.tp.register.share.imgUrl"), SYNCHRONIZE_IMAGE_FILE(
			"mobile.tp.register.share.filepath");

	private String name;

	private SynchronizeTpMobileTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
