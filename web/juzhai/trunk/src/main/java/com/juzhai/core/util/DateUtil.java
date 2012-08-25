package com.juzhai.core.util;

import java.util.Calendar;

public class DateUtil {
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
