/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.bean;

import org.apache.commons.lang.StringUtils;

/**
 * @author wujiajun Created on 2011-2-15
 */
public enum ThirdpartyNameEnum {

	// 新浪微博
	WEIBO("weibo"),
	// 人人
	RENREN("renren"),
	// qqzone
	QQ("qq"),
	// 腾讯微博
	TQQ("tQQ"),
	// 豆瓣
	DOUBAN("douban"),
	// 开心网
	KAIXIN001("kaixin001"),
	// facebook
	FACEBOOK("facebook");

	private String name;

	private ThirdpartyNameEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static ThirdpartyNameEnum getThirdpartyNameEnum(String name) {
		for (ThirdpartyNameEnum thirdpartyNameEnum : values()) {
			if (StringUtils.equals(name, thirdpartyNameEnum.getName())) {
				return thirdpartyNameEnum;
			}
		}
		return null;
	}
}
