package com.juzhai.core.image;

public enum JzImageSizeType {
	MIDDLE(200), BIG(450), ORIGINAL(0);
	private int type;

	private JzImageSizeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static JzImageSizeType getSizeTypeBySize(int size) {
		for (JzImageSizeType sizeType : values()) {
			if (sizeType.getType() == size) {
				return sizeType;
			}
		}
		return null;
	}
}
