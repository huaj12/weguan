package com.juzhai.core.web.jstl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/**
	 * 显示空闲时间
	 * 
	 * @param freeDateList
	 */
	public static String showFreeDates(List<Date> freeDateList, int count) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "日");
		map.put(2, "一");
		map.put(3, "二");
		map.put(4, "三");
		map.put(5, "四");
		map.put(6, "五");
		map.put(7, "六");
		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		int todayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < freeDateList.size(); i++) {
			if (i >= count) {
				break;
			}
			c.setTime(freeDateList.get(i));
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if (i > 0) {
				sb.append(",");
			}
			if (dayOfWeek == todayOfWeek) {
				sb.append("今天");
			} else if (dayOfWeek == todayOfWeek + 1
					|| (dayOfWeek == 1 && todayOfWeek == 7)) {
				sb.append("明天");
			} else if (dayOfWeek > todayOfWeek || dayOfWeek == 1) {
				sb.append("本周").append(map.get(dayOfWeek));
			} else {
				sb.append("下周").append(map.get(dayOfWeek));
			}
		}

		return sb.toString();
	}
}
