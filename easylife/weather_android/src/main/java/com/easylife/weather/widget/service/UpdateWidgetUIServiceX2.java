package com.easylife.weather.widget.service;

import java.util.Calendar;

import org.springframework.util.StringUtils;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.widget.WeatherWidgetProviderX2;

public class UpdateWidgetUIServiceX2 extends Service {

	public static Context context;
	public static AppWidgetManager appWidgetManager;
	public static RemoteViews remoteViews;
	private WeatherInfo weatherInfo;

	// 覆盖基类的抽象方法
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// 在本服务创建时将监听系统时间的BroadcastReceiver注册
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("service2", "--service created--");
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_TIME_TICK); // 时间的流逝
		intentFilter.addAction(Intent.ACTION_TIME_CHANGED); // 时间被改变，人为设置时间
		intentFilter.addAction(Constants.WIDGET_UPDATE_INTENT);
		registerReceiver(boroadcastReceiver, intentFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("service2", "--service started--");
		updateUI(); // 开始服务前先刷新一次UI
		return START_STICKY;
	}

	// 在服务停止时解注册BroadcastReceiver
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(boroadcastReceiver);
	}

	// 用于监听系统时间变化Intent.ACTION_TIME_TICK的BroadcastReceiver，此BroadcastReceiver须为动态注册
	private BroadcastReceiver boroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context acontext, Intent intent) {
			Log.e("time received2", "--receive--" + intent.getAction());
			updateUI();
		}
	};

	// 根据当前时间设置小部件相应的数字图片
	private void updateUI() {
		try {
			weatherInfo = WeatherDataManager.getWeatherInfos(
					DateUtil.getToday(), UpdateWidgetUIServiceX2.this);
			if (weatherInfo == null) {
				return;
			}
			Calendar cal = Calendar.getInstance();
			remoteViews.setTextViewText(R.id.widget_time,
					DateFormat.format("kk:mm", cal.getTime()));
			remoteViews.setTextViewText(
					R.id.widget_date,
					context.getResources().getString(
							R.string.weather_widget_date, "",
							DateUtil.WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1],
							DateFormat.format("MM/dd", cal.getTime())));
			if (weatherInfo != null
					&& StringUtils.hasText(weatherInfo.getSky())) {
				remoteViews
						.setTextViewText(R.id.sky_text, weatherInfo.getSky());
				remoteViews
						.setTextViewText(R.id.tmp_text_range, WeatherUtils
								.getTmpRange(weatherInfo,
										UpdateWidgetUIServiceX2.this));
			}
			// 将AppWidgetProvider的子类包装成ComponentName对象
			ComponentName componentName = new ComponentName(context,
					WeatherWidgetProviderX2.class);
			// 调用AppWidgetManager将remoteViews添加到ComponentName中
			appWidgetManager.updateAppWidget(componentName, remoteViews);
		} catch (Exception e) {

		}
	}

}
