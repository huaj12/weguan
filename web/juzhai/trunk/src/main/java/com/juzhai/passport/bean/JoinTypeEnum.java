/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.bean;

import org.apache.commons.lang.StringUtils;

/**
 * @author wujiajun Created on 2011-5-10
 */
public enum JoinTypeEnum {
	APP("app"), CONNECT("connect"), PLUS("plus");

	private String name;

	private JoinTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static JoinTypeEnum getJoinTypeEnum(String name) {
		for (JoinTypeEnum joinTypeEnum : values()) {
			if (StringUtils.equals(name, joinTypeEnum.getName())) {
				return joinTypeEnum;
			}
		}
		return null;
	}
}
