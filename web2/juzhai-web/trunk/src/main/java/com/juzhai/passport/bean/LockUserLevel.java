package com.juzhai.passport.bean;

import com.juzhai.passport.LockLevelConfig;

public enum LockUserLevel {
	LEVEL1(1, "level_1", 3), LEVEL2(2, "level_2", 5), LEVEL3(3, "level_3", 10);

	private int level;
	private String name;
	private int reportNumber;// 被举报的次数

	private LockUserLevel(int level, String name, int reportNumber) {
		this.level = level;
		this.name = name;
		this.reportNumber = reportNumber;
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

	public int getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(int reportNumber) {
		this.reportNumber = reportNumber;
	}

}
