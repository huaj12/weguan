package com.juzhai.post.bean;

public enum VerifyType {
	RAW(0), QUALIFIED(1), SHIELD(2);

	private int type;

	private VerifyType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
