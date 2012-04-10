package com.juzhai.verifycode.bean;


public enum VerifyLevel {
	LEVEL1(0), LEVEL2(10), LEVEL3(20), LEVEL4(30);
	private int level;

	private VerifyLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static VerifyLevel getVerifyLevel(int level) {
		for (VerifyLevel verifyLevel : values()) {
			if (level == verifyLevel.getLevel()) {
				return verifyLevel;
			}
		}
		return null;
	}
}
