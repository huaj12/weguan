package com.juzhai.android.home.bean;

public enum OnlineStatus {
	NOW(0), TODAY(1), RECENT(2), NONE(3);

	private int type;

	private OnlineStatus(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static OnlineStatus getOnlineStatusEnum(int type) {
		for (OnlineStatus onlineStatus : values()) {
			if (type == onlineStatus.getType()) {
				return onlineStatus;
			}
		}
		return null;
	}
}
