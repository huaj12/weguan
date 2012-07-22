package com.juzhai.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormat {

	public static String[] DATE_PATTERN = new String[] { "yyyy-MM-dd" };

	public static String[] TIME_PATTERN = new String[] { "yyyy-MM-dd HH:mm:ss" };

	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat SDF_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	//TODO (review) 放在这里合适吗？
	public static int getNextDayTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour > 6) {
			cal.add(Calendar.DATE, +1);
		}
		cal.set(Calendar.HOUR_OF_DAY, 6);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		int time = (int) (cal.getTime().getTime() / 1000);
		return time;
	}
}
