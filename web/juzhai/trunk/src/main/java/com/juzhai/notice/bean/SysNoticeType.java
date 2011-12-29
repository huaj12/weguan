package com.juzhai.notice.bean;

public enum SysNoticeType {

	ADD_ACT_SUCCESS("addActSuccess"), ADD_ACT_FAILURE("addActFailure");

	private String name;

	private SysNoticeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
