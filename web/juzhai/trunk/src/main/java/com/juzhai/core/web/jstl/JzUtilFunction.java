package com.juzhai.core.web.jstl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.juzhai.core.util.TextTruncateUtil;

public class JzUtilFunction {

	/**
	 * 截取字符串
	 * 
	 * @param originalText
	 * @param targetLength
	 * @param suffix
	 * @return
	 */
	public static String truncate(String originalText, int targetLength,
			String suffix) {
		return TextTruncateUtil.truncate(originalText, targetLength, suffix);
	}

	/**
	 * 比较时间
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateAfter(Date date) {
		return date.before(new Date());
	}

	/**
	 * 多少秒之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeSeconds(Date date) {
		return (System.currentTimeMillis() - date.getTime()) / 1000;
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

	/**
	 * 多少天之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeDays(Date date) {
		return ((DateUtils.truncate(new Date(), Calendar.DATE).getTime() - DateUtils
				.truncate(date, Calendar.DATE).getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 显示几岁
	 * 
	 * @param year
	 * @return
	 */
	public static int age(Integer year) {
		if (null == year || year <= 1900) {
			return -1;
		}
		Calendar c = Calendar.getInstance();
		int cYear = c.get(Calendar.YEAR);
		return cYear - year;
	}
}
