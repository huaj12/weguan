package com.juzhai.passport.bean;

public enum LockUserLevel {
	LEVEL1(1), LEVEL2(2), LEVEL3(3);

	private int type;

	private LockUserLevel(int type) {
		this.type = type;
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
		// TODO (review) 通过配置文件，load进内存
		switch (LockUserLevel.getLockUserLevelEnum(type)) {
		case LEVEL1:
			time = 1 * 24 * 60 * 60 * 1000;
			break;
		case LEVEL2:
			time = 7 * 24 * 60 * 60 * 1000;
			break;
		case LEVEL3:
			time = 10 * 365 * 24 * 60 * 60 * 1000;
			break;
		}
		time = System.currentTimeMillis() + time;
		return time;
	}
}
