package com.juzhai.passport.bean;

public enum Municipal {
	SHANGHAI("上海"), BEIJING("北京"), TIANJING("天津"), CHONGQING("重庆");
	private String name;

	private Municipal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Municipal getMunicipalEnum(String name) {
		for (Municipal joinTypeEnum : values()) {
			if (joinTypeEnum.getName().equals(name)) {
				return joinTypeEnum;
			}
		}
		return null;
	}
}
