package com.juzhai.notice.bean;

public enum NoticeQplusUserTemplate {
	NOTICE_QPLUG_USER_TEXT_DEFAULT("notice.qplus.user.text.default");

	private String name;

	private NoticeQplusUserTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
