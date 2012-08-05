package com.juzhai.notice.bean;

public enum NoticeQplusUserTemplate {
	NOTICE_QPLUS_USER_DEFAULT("notice.qplus.user.text.default",
			"notice.qplus.user.link.default"), NOTICE_QPLUS_USER_IDEA(
			"notice.qplus.user.text.idea", "notice.qplus.user.link.idea"), ;

	private String name;

	private String link;

	private NoticeQplusUserTemplate(String name, String link) {
		this.name = name;
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

}
