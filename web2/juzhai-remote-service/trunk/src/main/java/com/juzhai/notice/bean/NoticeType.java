package com.juzhai.notice.bean;

public enum NoticeType {
	// INTEREST_ME("/home/interestMes", 1),
	// DATING_ME("/home/datingMes", 2),
	// ACCEPT_DATING("/home/datings/accept", 3),
	// SYS_NOTICE("/sysnotice/list", 4),
	DIALOG("/home/dialog", 5), COMMENT("/home/comment/inbox/1", 6), VISITOR(
			"/home/visitors", 7);

	private String uri;

	private int type;

	private NoticeType(String uri, int type) {
		this.uri = uri;
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public int getType() {
		return type;
	}

	public static NoticeType getNoticeTypeByType(int type) {
		for (NoticeType noticeType : values()) {
			if (noticeType.getType() == type) {
				return noticeType;
			}
		}
		return null;
	}
}
