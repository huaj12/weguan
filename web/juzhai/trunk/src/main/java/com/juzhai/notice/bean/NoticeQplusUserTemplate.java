package com.juzhai.notice.bean;

public enum NoticeQplusUserTemplate {
	NOTICE_QPLUS_USER_TEXT_DEFAULT("notice.qplus.user.text.default"), NOTICE_QPLUS_USER_TEXT_IDEA(
			"notice.qplus.user.text.idea"), NOTICE_QPLUS_USER_LINK_DEFAULT(
			"notice.qplus.user.link.default");

	private String name;

	private NoticeQplusUserTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
