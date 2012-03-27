package com.juzhai.passport.bean;

public enum ReportHandleEnum {
	HANDLEING(0), HANDLED(1), INVALID(2);

	private int type;

	private ReportHandleEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
