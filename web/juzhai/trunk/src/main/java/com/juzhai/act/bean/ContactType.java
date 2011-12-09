package com.juzhai.act.bean;

public enum ContactType {
	QQ(1);

	private int value;

	private ContactType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ContactType getContactTypeByValue(int value) {
		for (ContactType contactType : ContactType.values()) {
			if (contactType.getValue() == value) {
				return contactType;
			}
		}
		return null;
	}
}
