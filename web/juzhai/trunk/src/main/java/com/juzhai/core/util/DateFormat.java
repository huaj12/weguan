package com.juzhai.core.util;

import java.text.SimpleDateFormat;

public class DateFormat {

	public static String[] DATE_PATTERN = new String[] { "yyyy-MM-dd" };

	public static String[] TIME_PATTERN = new String[] { "yyyy-MM-dd HH:mm:ss" };

	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat SDF_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
}
