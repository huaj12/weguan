package com.juzhai.passport.bean;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public enum OnlineStatus {
	NOW(0), TODAY(1), RECENT(2), NONE(3);

	private int type;

	private OnlineStatus(int type) {
		this.type = type;
	}

	/**
	 * 获取当前用户在线状态
	 * 
	 * @param date
	 *            最后在线时间
	 * @return
	 */
	public static OnlineStatus getStatus(Date date) {
		if (date == null) {
			return OnlineStatus.NONE;
		}
		long days = beforeDays(date);
		long minutes = beforeMinutes(date);
		if (minutes >= 0 && minutes < 60) {
			return OnlineStatus.NOW;
		} else if (minutes > 0 && minutes / 60 < 24) {
			return OnlineStatus.TODAY;
		} else if (days >= 1 && days < 10) {
			return OnlineStatus.RECENT;
		} else {
			return OnlineStatus.NONE;
		}
	}

	public int getType() {
		return type;
	}

	/**
	 * 多少天之前
	 * 
	 * @param date
	 * @return
	 */
	private static long beforeDays(Date date) {
		return ((DateUtils.truncate(new Date(), Calendar.DATE).getTime() - DateUtils
				.truncate(date, Calendar.DATE).getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 多少分钟之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeMinutes(Date date) {
		return (System.currentTimeMillis() - date.getTime()) / 60000;
	}
}
