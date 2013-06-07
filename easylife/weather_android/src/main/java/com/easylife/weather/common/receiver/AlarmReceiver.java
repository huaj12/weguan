package com.easylife.weather.common.receiver;

import org.springframework.util.StringUtils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.activity.LaunchActivity;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.main.service.IWeatherDataService;
import com.easylife.weather.main.service.impl.WeatherDataService;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;

public class AlarmReceiver extends BroadcastReceiver {
	private WifiManager.WifiLock mWifiLock;
	private int smsNoticeType = 0;

	@Override
	public void onReceive(final Context context, Intent intent) {
		if (Constants.ALARM_INTENT.equals(intent.getAction())) {
			Log.d("weather", "alarmReceiver is begin");
			WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (wm != null && !wm.isWifiEnabled()) {
				wm.setWifiEnabled(true);// 打开wifi
			}
			if (wm != null) {
				if (mWifiLock == null) {
					mWifiLock = wm.createWifiLock("spiderWifiLock");// 创建一个
					mWifiLock.setReferenceCounted(true);
				}
				if (!mWifiLock.isHeld())
					mWifiLock.acquire();// 得到锁
			}
			// // 执行任务
			// String cityName = UserConfigManager.getCityName(context);
			// if (StringUtils.hasText(cityName)) {
			// IWeatherDataService weatherDataService = new
			// WeatherDataService();
			// // 先清除数据然后再像服务端请求数据
			// // WeatherDataManager.delWeatherDate(context);
			// Log.d("weather", "updateWeatherDate is begin");
			// weatherDataService.updateWeatherDate(cityName, context);
			// Log.d("weather", "updateWeatherDate is ebd");
			// if (mWifiLock != null && mWifiLock.isHeld()) {
			// mWifiLock.release();
			// }
			// }
			// Log.d("weather", "send notification message begin");
			// if (!WeatherUtils.isAppOnForeground(context)) {
			// WeatherInfo weatherInfo = WeatherDataManager.getWeatherInfos(
			// DateUtil.getToday(), context);
			// if (weatherInfo == null) {
			// Log.d("weather", "weather info data is null");
			// return;
			// }
			// String title = context.getResources().getString(
			// R.string.notification_title);
			// String message = userRemind(
			// weatherInfo,
			// WeatherDataManager.getWeatherInfos(
			// DateUtil.getYesterday(), context), context);
			// if (StringUtils.hasText(message)) {
			// Intent msgIntent = new Intent(context, LaunchActivity.class);
			// msgIntent.setAction(Intent.ACTION_MAIN);
			// msgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			// msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// PendingIntent pendingIntent = PendingIntent.getActivity(
			// context, 0, msgIntent,
			// PendingIntent.FLAG_UPDATE_CURRENT);
			// WeatherUtils.sendNotificationMessage(smsNoticeType, title,
			// message, pendingIntent, context);
			// Log.d("weather", "send notification message end");
			// }
			//
			// }
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// 执行任务
					String cityName = UserConfigManager.getCityName(context);
					if (StringUtils.hasText(cityName)) {
						IWeatherDataService weatherDataService = new WeatherDataService();
						// 先清除数据然后再像服务端请求数据
						WeatherDataManager.delWeatherDate(context);
						Log.d("weather", "updateWeatherDate is begin");
						weatherDataService.updateWeatherDate(cityName, context);
						Log.d("weather", "updateWeatherDate is end");
						if (mWifiLock != null && mWifiLock.isHeld()) {
							mWifiLock.release();
						}
					}
					return null;
				}

				protected void onPostExecute(Void result) {
					Log.d("weather", "send notification message begin");
					if (!WeatherUtils.isAppOnForeground(context)) {
						WeatherInfo weatherInfo = WeatherDataManager
								.getWeatherInfos(DateUtil.getToday(), context);
						if (weatherInfo == null) {
							Log.d("weather", "weather info data is null");
							return;
						}
						String title = context.getResources().getString(
								R.string.notification_title);
						String message = userRemind(
								weatherInfo,
								WeatherDataManager.getWeatherInfos(
										DateUtil.getYesterday(), context),
								context);
						if (StringUtils.hasText(message)) {
							Intent msgIntent = new Intent(context,
									LaunchActivity.class);
							msgIntent.setAction(Intent.ACTION_MAIN);
							msgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
							msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							PendingIntent pendingIntent = PendingIntent
									.getActivity(context, 0, msgIntent,
											PendingIntent.FLAG_UPDATE_CURRENT);
							WeatherUtils.sendNotificationMessage(smsNoticeType,
									title, message, pendingIntent, context);
							Log.d("weather", "send notification message end");
						}

					}
				};
			}.execute();
		} else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			WeatherUtils.setRepeating(context);
		}
	}

	private String userRemind(WeatherInfo info, WeatherInfo yesterdayInfo,
			Context context) {
		UserConfig user = UserConfigManager.getUserConfig(context);
		StringBuilder text = new StringBuilder();
		if (user.isRemindRain()) {
			String rainText = context.getResources().getString(R.string.rain);
			String dayTimeSky = StringUtils.hasText(info.getDaytimeSky()) ? info
					.getDaytimeSky() : "";
			String nightSky = StringUtils.hasText(info.getNightSky()) ? info
					.getNightSky() : "";
			String day = null;
			if (dayTimeSky.indexOf(rainText) != -1
					&& nightSky.indexOf(rainText) != -1) {
				day = context.getResources().getString(R.string.all_day);
			} else if (dayTimeSky.indexOf(rainText) != -1
					&& nightSky.indexOf(rainText) == -1) {
				day = context.getResources().getString(R.string.morning);
			} else if (dayTimeSky.indexOf(rainText) == -1
					&& nightSky.indexOf(rainText) != -1) {
				day = context.getResources().getString(R.string.night);
			}
			if (StringUtils.hasText(day)) {
				text.append(context.getResources().getString(
						R.string.remind_rain_text, day));
			}
		}
		if (user.isRemindCooling()) {
			try {
				int oldTmp = Integer.parseInt(yesterdayInfo.getMinTmp());
				int tmp = Integer.parseInt(info.getMinTmp());
				if (oldTmp - tmp >= Constants.TEMPERATURE
						&& tmp < Constants.COLLING_TMP_MIN) {
					text.append(context.getResources().getString(
							R.string.remind_tmp_text));
				}
			} catch (Exception e) {
			}
		}
		if (user.isRemindWind()) {
			try {
				int daytimeWind = WeatherUtils.getWindLevel(info
						.getDaytimeWindPower());
				int nightWind = WeatherUtils.getWindLevel(info
						.getNightWindPower());
				if (daytimeWind > 8 || nightWind > 8) {
					text.append(context.getResources().getString(
							R.string.remind_big_wind_text));
				} else if (daytimeWind > 6 || nightWind > 6) {
					text.append(context.getResources().getString(
							R.string.remind_wind));
				}
			} catch (Exception e) {
			}
		}
		if (StringUtils.hasText(text.toString())) {
			return context.getResources().getString(R.string.remind_text,
					text.toString());
		} else {
			return null;
		}
	}

}
