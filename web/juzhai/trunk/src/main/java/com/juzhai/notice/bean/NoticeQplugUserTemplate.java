package com.juzhai.notice.bean;

public enum NoticeQplugUserTemplate {
	NOTICE_QPLUG_USER_TEXT_DEFAULT("notice.qplug.user.text.default");

	private String name;

	private NoticeQplugUserTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
