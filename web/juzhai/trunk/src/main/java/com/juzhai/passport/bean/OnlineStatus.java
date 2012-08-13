package com.juzhai.passport.bean;

import java.util.Date;

import com.juzhai.core.web.jstl.JzUtilFunction;

public enum OnlineStatus {
	NOW("now"), TODAY("today"), RECENT("recent");

	private String name;

	private OnlineStatus(String name) {
		this.name = name;
	}

	/**
	 * 获取当前用户在线状态
	 * 
	 * @param date
	 *            最后在线时间
	 * @return
	 */
	public static OnlineStatus getStatus(Date date) {
		long days = JzUtilFunction.beforeDays(date);
		long minutes = JzUtilFunction.beforeMinutes(date);
		if (minutes >= 0 && minutes < 60) {
			return OnlineStatus.NOW;
		} else if (minutes > 0 && minutes / 60 < 24) {
			return OnlineStatus.TODAY;
		} else if (days >= 1 && days < 10) {
			return OnlineStatus.RECENT;
		} else {
			return null;
		}
	}

}
