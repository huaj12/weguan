package com.android.weatherspider.core.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.android.weatherspider.R;
import com.android.weatherspider.core.data.SharedPreferencesManager;
import com.android.weatherspider.core.service.IWeatherSpiderService;
import com.android.weatherspider.core.service.impl.WeatherSpiderService;
import com.android.weatherspider.core.utils.WeatherSpiderUtils;

public class AlarmReceiver extends BroadcastReceiver {
	private IWeatherSpiderService weatherSpiderService = new WeatherSpiderService();
	private WifiManager.WifiLock mWifiLock;
	private long noticePeriod = 1000 * 3600 * 24;

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.alarm.spider.action".equals(intent.getAction())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
			SharedPreferencesManager dataManager = new SharedPreferencesManager(
					context);
			dataManager
					.commit(SharedPreferencesManager.HAS_RUN_TASK_TEXT,
							context.getResources().getString(
									R.string.has_run_task_text,
									sdf.format(new Date())));
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
			Log.d("spider", "spider is begin");
			weatherSpiderService.spider(context);
			Log.d("spider", "spider is end");
			if (mWifiLock != null && mWifiLock.isHeld())
				mWifiLock.release();
			// if (wm != null) {
			// wm.setWifiEnabled(false);
			// }
		} else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					new Intent("android.alarm.spider.action"),
					PendingIntent.FLAG_CANCEL_CURRENT);
			am.setRepeating(AlarmManager.RTC_WAKEUP,
					WeatherSpiderUtils.getTriggerAtMillis(context),
					noticePeriod, sender);
		}
	}
}
