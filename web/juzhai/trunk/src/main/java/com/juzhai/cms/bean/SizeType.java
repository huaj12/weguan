package com.juzhai.cms.bean;


public enum SizeType {
	SMALL(50), MIDDLE(120), BIG(0);//原图
	private int type;

	private SizeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
