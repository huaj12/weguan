package com.juzhai.core.image;

public enum LogoSizeType {
	SMALL(80), MIDDLE(120), BIG(180), ORIGINAL(0), RAW(-1);
	private int type;

	private LogoSizeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static LogoSizeType getSizeTypeBySize(int size) {
		for (LogoSizeType sizeType : values()) {
			if (sizeType.getType() == size) {
				return sizeType;
			}
		}
		return null;
	}
}
