package com.juzhai.act.bean;

public enum ConsumeType {
	TREAT(1), AA(2), TREATED(3), FREE(4);

	private int value;

	private ConsumeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ConsumeType getConsumeTypeByValue(int value) {
		for (ConsumeType consumeType : ConsumeType.values()) {
			if (consumeType.getValue() == value) {
				return consumeType;
			}
		}
		return null;
	}
}
