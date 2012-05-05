package com.juzhai.passport.bean;

import com.juzhai.passport.LockLevelConfig;

public enum LockUserLevel {
	LEVEL1(1, "level_1"), LEVEL2(2, "level_2"), LEVEL3(3, "level_3");

	private int level;
	private String name;

	private LockUserLevel(int level, String name) {
		this.level = level;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getLockTime() {
		return LockLevelConfig.LOCKLEVEL_MAP.get(this.name);
	}

	public static LockUserLevel getLockUserLevelEnum(int type) {
		for (LockUserLevel lockUserLevel : values()) {
			if (type == lockUserLevel.getLevel()) {
				return lockUserLevel;
			}
		}
		return null;
	}

}
