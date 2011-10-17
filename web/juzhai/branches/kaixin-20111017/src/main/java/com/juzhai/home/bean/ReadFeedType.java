package com.juzhai.home.bean;

public enum ReadFeedType {
	DEPEND(0), WANT(1), NILL(2);

	private int type;

	private ReadFeedType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static ReadFeedType valueOf(int type) {
		for (ReadFeedType actDealType : ReadFeedType.values()) {
			if (actDealType.getType() == type) {
				return actDealType;
			}
		}
		return null;
	}
}
