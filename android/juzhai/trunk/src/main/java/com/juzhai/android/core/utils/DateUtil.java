package com.juzhai.android.core.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * Constant marker for truncating
	 */
	private final static int MODIFY_TRUNCATE = 0;

	/**
	 * Constant marker for rounding
	 */
	private final static int MODIFY_ROUND = 1;

	/**
	 * Constant marker for ceiling
	 */
	private final static int MODIFY_CEILING = 2;

	/**
	 * This is half a month, so this represents whether a date is in the top or
	 * bottom half of the month.
	 */
	public final static int SEMI_MONTH = 1001;

	private static final int[][] fields = { { Calendar.MILLISECOND },
			{ Calendar.SECOND }, { Calendar.MINUTE },
			{ Calendar.HOUR_OF_DAY, Calendar.HOUR },
			{ Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM
			/*
			 * Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK,
			 * Calendar.DAY_OF_WEEK_IN_MONTH
			 */
			}, { Calendar.MONTH, SEMI_MONTH }, { Calendar.YEAR },
			{ Calendar.ERA } };

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Truncate this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date truncate(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_TRUNCATE);
		return gval.getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Internal calculation method.
	 * </p>
	 * 
	 * @param val
	 *            the calendar
	 * @param field
	 *            the field constant
	 * @param modType
	 *            type to truncate, round or ceiling
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	private static void modify(Calendar val, int field, int modType) {
		if (val.get(Calendar.YEAR) > 280000000) {
			throw new ArithmeticException(
					"Calendar value too large for accurate calculations");
		}

		if (field == Calendar.MILLISECOND) {
			return;
		}

		// ----------------- Fix for LANG-59 ---------------------- START
		// ---------------
		// see http://issues.apache.org/jira/browse/LANG-59
		//
		// Manually truncate milliseconds, seconds and minutes, rather than
		// using
		// Calendar methods.

		Date date = val.getTime();
		long time = date.getTime();
		boolean done = false;

		// truncate milliseconds
		int millisecs = val.get(Calendar.MILLISECOND);
		if (MODIFY_TRUNCATE == modType || millisecs < 500) {
			time = time - millisecs;
		}
		if (field == Calendar.SECOND) {
			done = true;
		}

		// truncate seconds
		int seconds = val.get(Calendar.SECOND);
		if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
			time = time - (seconds * 1000L);
		}
		if (field == Calendar.MINUTE) {
			done = true;
		}

		// truncate minutes
		int minutes = val.get(Calendar.MINUTE);
		if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
			time = time - (minutes * 60000L);
		}

		// reset time
		if (date.getTime() != time) {
			date.setTime(time);
			val.setTime(date);
		}
		// ----------------- Fix for LANG-59 ----------------------- END
		// ----------------

		boolean roundUp = false;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] == field) {
					// This is our field... we stop looping
					if (modType == MODIFY_CEILING
							|| (modType == MODIFY_ROUND && roundUp)) {
						if (field == SEMI_MONTH) {
							// This is a special case that's hard to generalize
							// If the date is 1, we round up to 16, otherwise
							// we subtract 15 days and add 1 month
							if (val.get(Calendar.DATE) == 1) {
								val.add(Calendar.DATE, 15);
							} else {
								val.add(Calendar.DATE, -15);
								val.add(Calendar.MONTH, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- START ---------------
						} else if (field == Calendar.AM_PM) {
							// This is a special case
							// If the time is 0, we round up to 12, otherwise
							// we subtract 12 hours and add 1 day
							if (val.get(Calendar.HOUR_OF_DAY) == 0) {
								val.add(Calendar.HOUR_OF_DAY, 12);
							} else {
								val.add(Calendar.HOUR_OF_DAY, -12);
								val.add(Calendar.DATE, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- END ---------------
						} else {
							// We need at add one to this field since the
							// last number causes us to round up
							val.add(fields[i][0], 1);
						}
					}
					return;
				}
			}
			// We have various fields that are not easy roundings
			int offset = 0;
			boolean offsetSet = false;
			// These are special types of fields that require different rounding
			// rules
			switch (field) {
			case SEMI_MONTH:
				if (fields[i][0] == Calendar.DATE) {
					// If we're going to drop the DATE field's value,
					// we want to do this our own way.
					// We need to subtrace 1 since the date has a minimum of 1
					offset = val.get(Calendar.DATE) - 1;
					// If we're above 15 days adjustment, that means we're in
					// the
					// bottom half of the month and should stay accordingly.
					if (offset >= 15) {
						offset -= 15;
					}
					// Record whether we're in the top or bottom half of that
					// range
					roundUp = offset > 7;
					offsetSet = true;
				}
				break;
			case Calendar.AM_PM:
				if (fields[i][0] == Calendar.HOUR_OF_DAY) {
					// If we're going to drop the HOUR field's value,
					// we want to do this our own way.
					offset = val.get(Calendar.HOUR_OF_DAY);
					if (offset >= 12) {
						offset -= 12;
					}
					roundUp = offset >= 6;
					offsetSet = true;
				}
				break;
			}
			if (!offsetSet) {
				int min = val.getActualMinimum(fields[i][0]);
				int max = val.getActualMaximum(fields[i][0]);
				// Calculate the offset from the minimum allowed value
				offset = val.get(fields[i][0]) - min;
				// Set roundUp if this is more than half way between the minimum
				// and maximum
				roundUp = offset > ((max - min) / 2);
			}
			// We need to remove this field
			if (offset != 0) {
				val.set(fields[i][0], val.get(fields[i][0]) - offset);
			}
		}
		throw new IllegalArgumentException("The field " + field
				+ " is not supported");

	}
}
