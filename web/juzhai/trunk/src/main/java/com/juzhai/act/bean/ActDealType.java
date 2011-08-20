package com.juzhai.act.bean;

public enum ActDealType {
	DEPEND(0), WANT(1), NILL(2);

	private int type;

	private ActDealType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static ActDealType valueOf(int type) {
		for (ActDealType actDealType : ActDealType.values()) {
			if (actDealType.getType() == type) {
				return actDealType;
			}
		}
		return null;
	}
}
