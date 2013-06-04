package com.android.weatherspider.core.utils;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.weatherspider.core.data.SharedPreferencesManager;

public class WeatherSpiderUtils {
	public static boolean hasWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	public static long getTriggerAtMillis(Context context) {
		SharedPreferencesManager manager = new SharedPreferencesManager(context);
		int dayHour = manager.getInt(SharedPreferencesManager.HOUR_OF_DAY, 3);
		int minute = manager.getInt(SharedPreferencesManager.MINUTE, 0);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, dayHour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, 0);
		long time = cal.getTimeInMillis() - new Date().getTime();
		if (time > 0) {
			return cal.getTime().getTime();
		} else {
			return cal.getTimeInMillis() + (1000 * 3600 * 24);
		}
	}
}
