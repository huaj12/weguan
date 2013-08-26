package com.easylife.weather.core.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.util.StringUtils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;

public class WeatherUtils {
	private static final int[] WEATHER_TYEP = { R.drawable.btyy_normal,
			R.drawable.wsyy_normal, R.drawable.qtyy_normal,
			R.drawable.wdzj_normal, R.drawable.dflx_normal,
			R.drawable.kqwr_normal, R.drawable.jrgw_normal };

	public static int getRainResource(WeatherInfo info, Context context) {
		int resource = 0;
		if (info == null || !StringUtils.hasText(info.getDaytimeSky())
				|| !StringUtils.hasText(info.getNightSky())) {
			return 0;
		}
		String rainText = context.getResources().getString(R.string.rain);
		if (info.getDaytimeSky().indexOf(rainText) != -1
				&& info.getNightSky().indexOf(rainText) != -1) {
			resource = R.drawable.qtyy_normal;
		} else if (info.getDaytimeSky().indexOf(rainText) != -1
				&& info.getNightSky().indexOf(rainText) == -1) {
			resource = R.drawable.btyy_normal;
		} else if (info.getDaytimeSky().indexOf(rainText) == -1
				&& info.getNightSky().indexOf(rainText) != -1) {
			resource = R.drawable.wsyy_normal;
		}
		return resource;
	}

	public static int getWindResource(WeatherInfo info) {
		int resource = 0;
		try {
			int daytimeWind = getWindLevel(info.getDaytimeWindPower());
			int nightWind = getWindLevel(info.getNightWindPower());
			if (daytimeWind > 6 || nightWind > 6) {
				resource = R.drawable.dflx_normal;
			}
		} catch (Exception e) {

		}
		return resource;
	}

	public static int getPM25Resource(WeatherInfo info) {
		int resource = 0;
		int pm25 = getPM2Level(info);
		if (pm25 > 3) {
			resource = R.drawable.kqwr_normal;
		}
		return resource;
	}

	public static int getTmpResource(WeatherInfo info) {
		int resource = 0;
		try {
			if (Integer.parseInt(info.getMaxTmp()) >= Constants.HOT_TMP) {
				resource = R.drawable.jrgw_normal;
			}
		} catch (Exception e) {
		}
		return resource;
	}

	public static int getCoolingResource(WeatherInfo yesterdayInfo,
			WeatherInfo todayInfo) {
		int resource = 0;
		try {
			int oldTmp = Integer.parseInt(yesterdayInfo.getMinTmp());
			int tmp = Integer.parseInt(todayInfo.getMinTmp());
			if (oldTmp - tmp >= Constants.TEMPERATURE
					&& tmp < Constants.COLLING_TMP_MIN) {
				resource = R.drawable.wdzj_normal;
			}
		} catch (Exception e) {
		}
		return resource;
	}

	/**
	 * 获取风力级别
	 * 
	 * @param wind
	 * @return
	 */
	public static int getWindLevel(String wind) {
		int windLevel = 0;
		try {
			if (wind.indexOf("-") != -1) {
				// xx-xx
				String[] str = wind.split("-");
				if (str.length > 1) {
					windLevel = Integer.parseInt(str[1].trim());
				}
			} else if (wind.indexOf("<") != -1) {
				// 小于N最大值为n-1
				windLevel = Integer.parseInt(wind.replace("<", "").trim()) - 1;
			} else if (wind.indexOf(">") != -1) {
				// 大于N最大值为n+1
				windLevel = Integer.parseInt(wind.replace(">", "").trim()) + 1;
			} else {
				windLevel = Integer.parseInt(wind);
			}
		} catch (Exception e) {
		}
		return windLevel;
	}

	public static int getPM2Level(WeatherInfo info) {
		// 0-50 优50-100良 100-150 轻度污染 150-300中毒污染 >300重度污染
		int pm25 = 0;
		try {
			if (info.getAir() == null) {
				return 0;
			}
			int level = Integer.parseInt(info.getAir().getLevel());
			if (level > 50 && level <= 100) {
				pm25 = 2;
			} else if (level > 100 && level <= 150) {
				pm25 = 3;
			} else if (level > 150 && level <= 300) {
				pm25 = 4;
			} else if (level > 300) {
				pm25 = 5;
			} else {
				pm25 = 1;
			}
		} catch (Exception e) {
		}
		return pm25;
	}

	public static String getDeviceToken(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		if (StringUtils.hasText(deviceId)) {
			return deviceId;
		}
		SharedPreferencesManager manager = new SharedPreferencesManager(context);
		if (manager.isExist(SharedPreferencesManager.DEVICE_TOKEN)) {
			return manager.getString(SharedPreferencesManager.DEVICE_TOKEN);
		}
		String deviceToken = UUID.randomUUID().toString();
		manager.commit(SharedPreferencesManager.DEVICE_TOKEN, deviceToken);
		return deviceToken;
	}

	public static String getforecastHourText(String time, Context context) {
		try {
			String dayStr = context.getResources().getString(R.string.day);
			String str[] = time.split(dayStr);
			String hour;
			if (str.length > 2) {
				hour = context.getResources()
						.getString(R.string.forecastHour_4);
			} else {
				String id = "forecastHour_";
				if ("08:00-14:00".equals(str[0])) {
					id = id + 2;
				} else if ("14:00-20:00".equals(str[0])) {
					id = id + 3;
				} else {
					id = id + 1;
				}
				hour = context.getResources().getString(
						context.getResources().getIdentifier(id, "string",
								context.getPackageName()));

			}
			String date = null;
			int day = Integer.parseInt(str[0]);
			Calendar cal = Calendar.getInstance();
			if (cal.get(Calendar.DAY_OF_MONTH) == day) {
				date = context.getResources().getString(
						R.string.forecastHour_today);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (cal.get(Calendar.DAY_OF_MONTH) == day) {
				date = context.getResources().getString(
						R.string.forecastHour_tomorrow);
			}
			if (date == null) {
				date = context.getResources().getString(
						R.string.forecastHour_yesterday);
			}
			return context.getResources().getString(R.string.forecastHour_time,
					date, hour);
		} catch (Exception e) {
			return "";
		}

	}

	public static String getShareText(Integer[] res, Context context) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < WEATHER_TYEP.length; j++) {
				if (res[i] == WEATHER_TYEP[j]) {
					int id = context.getResources().getIdentifier(
							"share_text" + j, "string",
							context.getPackageName());
					sb.append(context.getResources().getString(id));
				}
			}
		}
		return context.getResources().getString(R.string.share_weather,
				UserConfigManager.getCityName(context), sb.toString());
	}

	public static boolean isAppOnForeground(Context context) {
		String packageName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity
					.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public static void sendNotificationMessage(int id, String title,
			String text, PendingIntent pendingIntent, Context context) {
		// 构造Notification对象
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		// 设置通知在状态栏显示的图标
		notification.icon = R.drawable.logo_tz;
		// 当我们点击通知时显示的内容
		notification.tickerText = text;
		// 通知时发出默认的声音
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// 设置通知显示的参数
		notification.setLatestEventInfo(context, title, text, pendingIntent);
		notification.icon = R.drawable.logo_top;
		// 开始执行
		notificationManager.notify(id, notification);
	}

	public static long userRemindTime(Context context) {
		UserConfig user = UserConfigManager.getUserConfig(context);
		int hour = Constants.HOUR;
		int minute = 0;
		if (user != null && user.getTime() > 0) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(user.getTime());
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
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

	public static void setRepeating(Context context) {
		Log.d("weather", "setRepeating begin");
		long noticePeriod = 1000 * 3600 * 24;
		UserConfig user = UserConfigManager.getUserConfig(context);
		if (user == null
				|| (user.isRemindCooling() || user.isRemindRain()
						|| user.isRemindWind() || user.isRemindHot())) {
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					new Intent(Constants.ALARM_INTENT),
					PendingIntent.FLAG_CANCEL_CURRENT);
			am.setRepeating(AlarmManager.RTC_WAKEUP,
					WeatherUtils.userRemindTime(context), noticePeriod, sender);
			// am.setRepeating(AlarmManager.RTC_WAKEUP,
			// System.currentTimeMillis()
			// + (1000 * 30), noticePeriod, sender);
		}
		Log.d("weather", "setRepeating end");
	}

	public static void updateData(Context context) {
		long noticePeriod = 1000 * 1200;
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0,
				new Intent(Constants.UPDATE_DATA_INTENT),
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ noticePeriod, noticePeriod, sender);
	}

	public static void cancelUpdateData(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0,
				new Intent(Constants.UPDATE_DATA_INTENT),
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(sender);
	}

	public static String getTmpRange(WeatherInfo todayWeather, Context mContext) {
		String str = null;
		if (!StringUtils.hasText(todayWeather.getMinTmp())
				&& !StringUtils.hasText(todayWeather.getMaxTmp())) {
			str = mContext.getResources().getString(R.string.tmp,
					todayWeather.getNowTmp(), todayWeather.getNowTmp());
		} else if (StringUtils.hasText(todayWeather.getMinTmp())
				&& !StringUtils.hasText(todayWeather.getMaxTmp())) {
			str = mContext.getResources().getString(R.string.min_tmp,
					todayWeather.getMinTmp());
		} else if (!StringUtils.hasText(todayWeather.getMinTmp())
				&& StringUtils.hasText(todayWeather.getMaxTmp())) {
			str = mContext.getResources().getString(R.string.max_tmp,
					todayWeather.getMaxTmp());
		} else {
			str = mContext.getResources().getString(R.string.tmp,
					todayWeather.getMinTmp(), todayWeather.getMaxTmp());
		}
		return str;
	}
}
