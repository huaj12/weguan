package com.juzhai.android.core.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import android.content.Context;

import com.juzhai.android.R;
import com.juzhai.android.common.model.City;
import com.juzhai.android.core.model.Entity;

public class JzUtils {
	private final static Pattern FACE_PATTERN = Pattern.compile("\\[(.+?)\\]");

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

	public static String showTime(Context context, Date date) {
		long seconds = beforeSeconds(date);
		long minutes = beforeMinutes(date);
		long day = beforeDays(date);
		if (seconds < 60) {
			return context.getResources().getString(R.string.before_seconds);
		} else if (minutes > 0 && minutes < 60) {
			return minutes
					+ context.getResources().getString(R.string.before_minutes);
		} else if (minutes > 0 && minutes / 60 < 24) {
			return minutes / 60
					+ context.getResources().getString(R.string.before_hour);
		} else if (day == 1) {
			return context.getResources().getString(R.string.yesterday);
		} else if (day == 2) {
			return context.getResources().getString(R.string.before_yesterday);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			return sdf.format(date);
		}
	}

	public static String getImageUrl(String url) {
		return url.replaceAll("test.", "");
	}

	public static String getLogverifyStateString(int verifystate,
			Context context) {
		String str = null;
		switch (verifystate) {
		case -1:
			str = context.getResources().getString(R.string.verifying);
			break;
		case 1:
			str = context.getResources().getString(R.string.verifying);
			break;
		case 3:
			str = context.getResources().getString(R.string.verified);
			break;
		}
		return str;
	}

	public static String getGender(int gender, Context context) {
		String str = null;
		switch (gender) {
		case 0:
			str = context.getResources().getString(R.string.girl);
			break;
		case 1:
			str = context.getResources().getString(R.string.boy);
			break;
		}
		return str;
	}

	// TODO (review) 放到commonService里吧
	public static <T extends Entity> int getDataIndxex(long id, List<T> datas) {
		for (int i = 0; i < datas.size(); i++) {
			long identify = (Long) datas.get(i).getIdentify();
			if (identify == id) {
				return i;
			}
		}
		return 0;
	}

	// TODO (review) 放到commonService里吧
	public static List<City> getSelectCity(long provinceId, List<City> allCitys) {
		List<City> ciyts = new ArrayList<City>();
		for (City city : allCitys) {
			if (provinceId == city.getProvinceId()) {
				ciyts.add(city);
			}
		}
		return ciyts;
	}

}
