package com.juzhai.post.bean;

public enum PurposeType {

	WANT(0), PEOPLE(1), BOY(2), GIRL(3);

	private int type;

	private PurposeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static PurposeType getPurposeTypeByType(int type) {
		for (PurposeType purposeType : values()) {
			if (purposeType.getType() == type) {
				return purposeType;
			}
		}
		return null;
	}

	public static String getWordMessageKey(int type) {
		return "purpose.type." + type;
	}
}
