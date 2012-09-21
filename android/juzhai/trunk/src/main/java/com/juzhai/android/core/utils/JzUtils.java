package com.juzhai.android.core.utils;

import java.util.Calendar;

public class JzUtils {
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
