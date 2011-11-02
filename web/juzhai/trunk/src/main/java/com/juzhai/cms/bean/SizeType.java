package com.juzhai.cms.bean;

public enum SizeType {
	SMALL(80), MIDDLE(120), BIG(180), ORIGINAL(0), RAW(-1);
	private int type;

	private SizeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
