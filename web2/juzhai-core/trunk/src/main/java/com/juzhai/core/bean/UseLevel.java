package com.juzhai.core.bean;

//TODO 此类放这个包不合适，今后再说
public enum UseLevel {
	Level0(0), Level1(1);

	private int useLevel;

	private UseLevel(int useLevel) {
		this.useLevel = useLevel;
	}

	public int getUseLevel() {
		return useLevel;
	}

	public void setUseLevel(int useLevel) {
		this.useLevel = useLevel;
	}

}
