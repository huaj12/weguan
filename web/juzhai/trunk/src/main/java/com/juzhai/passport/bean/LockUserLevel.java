package com.juzhai.passport.bean;

import com.juzhai.passport.LockLevelConfig;

public enum LockUserLevel {
	LEVEL1(1, "level_1"), LEVEL2(2, "level_2"), LEVEL3(3, "level_3");

	private int type;
	private String name;

	private LockUserLevel(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public static LockUserLevel getLockUserLevelEnum(int type) {
		for (LockUserLevel lockUserLevel : values()) {
			if (type == lockUserLevel.getType()) {
				return lockUserLevel;
			}
		}
		return null;
	}

	public static long getLockTime(int type) {
		long time = 0;
		// TODO (done) 通过配置文件，load进内存
		switch (LockUserLevel.getLockUserLevelEnum(type)) {
		case LEVEL1:
			time = LockLevelConfig.LOCKLEVEL_MAP.get(LEVEL1.name);
			break;
		case LEVEL2:
			time = LockLevelConfig.LOCKLEVEL_MAP.get(LEVEL2.name);
			break;
		case LEVEL3:
			time = LockLevelConfig.LOCKLEVEL_MAP.get(LEVEL3.name);
			break;
		}
		time = System.currentTimeMillis() + time;
		return time;
	}
}
