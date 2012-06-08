package com.juzhai.passport.bean;

public enum ReportHandleEnum {
	HANDLEING(0), INVALID(1), HANDLED(2), AUTOHANDLED(3);

	private int type;

	private ReportHandleEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
