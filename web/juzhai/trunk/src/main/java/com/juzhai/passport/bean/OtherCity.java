package com.juzhai.passport.bean;

public enum OtherCity {
	XIANGGANG("香港"), AOMEN("澳门"),TAIWAN("台湾");
	private String name;

	private OtherCity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static OtherCity getOtherCityEnum(String name) {
		for (OtherCity joinTypeEnum : values()) {
			if (joinTypeEnum.getName().equals(name)) {
				return joinTypeEnum;
			}
		}
		return null;
	}
}
