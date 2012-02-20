package com.juzhai.passport.bean;

public enum LogoVerifyState {

	NONE(0), VERIFYING(1), VERIFIED(2), UNVERIFIED(3);

	private int type;

	private LogoVerifyState(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
