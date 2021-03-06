package com.juzhai.core.image;

public enum DialogSizeType {
	ORIGINAL(0);
	private int type;

	private DialogSizeType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static DialogSizeType getSizeTypeBySize(int size) {
		for (DialogSizeType sizeType : values()) {
			if (sizeType.getType() == size) {
				return sizeType;
			}
		}
		return null;
	}
}
