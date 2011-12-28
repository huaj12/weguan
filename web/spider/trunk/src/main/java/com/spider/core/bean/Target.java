package com.spider.core.bean;

public enum Target {
	TUAN800("tuan800"), DAMAI("damai");

	private String name;

	private Target(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Target getJoinTypeEnum(String name) {
		for (Target joinTypeEnum : values()) {
			if (joinTypeEnum.getName().equals(name)) {
				return joinTypeEnum;
			}
		}
		return null;
	}
}
