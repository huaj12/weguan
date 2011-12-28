package com.juzhai.notice.bean;

public enum NoticeType {
	INTERESTME("/home/interestMes", 1), DATINGME("/home/datingMes", 2), ACCEPTDATING(
			"/home/datings/accept", 3), SYSNOTICE("/sysnotice/list", 4);

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

}
