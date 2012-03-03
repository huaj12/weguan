package com.juzhai.notice.bean;

public enum NoticeUserTemplate {
	NOTICE_USER_TEXT_DEFAULT("notice.user.text.default");

	private String name;

	private NoticeUserTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
