package com.juzhai.android.dialog.bean;

public enum MessageStatus {
	WAIT(1), SENDING(2), ERROR(3), SUCCESS(4);
	private int type;

	private MessageStatus(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
