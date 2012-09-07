package com.juzhai.notice.bean;

public enum NoticeQplusUserTemplate {
	NOTICE_QPLUS_USER_DEFAULT("notice.qplus.user.text.default",
			"notice.qplus.user.link.default", 0,
			"notice.qplus.user.name.default"), NOTICE_QPLUS_USER_IDEA(
			"notice.qplus.user.text.idea", "notice.qplus.user.link.idea", 1,
			"notice.qplus.user.name.idea"), ;

	private String text;

	private String link;

	private int type;

	private String name;

	private NoticeQplusUserTemplate(String text, String link, int type,
			String name) {
		this.text = text;
		this.link = link;
		this.type = type;
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public String getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public static NoticeQplusUserTemplate getNoticeQplusUserTemplateEnum(
			int type) {
		for (NoticeQplusUserTemplate noticeQplusUserTemplate : values()) {
			if (type == noticeQplusUserTemplate.type) {
				return noticeQplusUserTemplate;
			}
		}
		return null;
	}

}
