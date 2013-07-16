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
import android.view.View;
import android.widget.RemoteViews;

import com.easylife.weather.R;
import com.easylife.weather.core.Constants;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.utils.DateUtil;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.data.WeatherDataManager;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.main.service.IWeatherDataService;
import com.easylife.weather.main.service.impl.WeatherDataService;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.widget.WeatherWidgetProvider;

public class UpdateWidgetUIService extends Service {

	public static Context context;
	public static AppWidgetManager appWidgetManager;
	public static RemoteViews remoteViews;
	private WeatherInfo weatherInfo;
	private IWeatherDataService weatherDataService = new WeatherDataService();
	private String cityName;

	// 覆盖基类的抽象方法
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// 在本服务创建时将监听系统时间的BroadcastReceiver注册
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("service", "--service created--");
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_TIME_TICK); // 时间的流逝
		intentFilter.addAction(Intent.ACTION_TIME_CHANGED); // 时间被改变，人为设置时间
		intentFilter.addAction(Constants.WIDGET_UPDATE_INTENT);
		registerReceiver(boroadcastReceiver, intentFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("service", "--service started--");
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
			Log.e("time received", "--receive--" + intent.getAction());
			updateUI();
		}
	};

	// 根据当前时间设置小部件相应的数字图片
	private void updateUI() {
		String cityName = UserConfigManager
				.getCityName(UpdateWidgetUIService.this);
		SharedPreferencesManager manager = new SharedPreferencesManager(
				UpdateWidgetUIService.this);
		long lastUpdateTime = manager
				.getLong(SharedPreferencesManager.LAST_UPDATE_TIME);
		if (System.currentTimeMillis() - lastUpdateTime < Constants.WEATHER_UPDATE_INTERVAL) {
			weatherInfo = WeatherDataManager.getWeatherInfos(
					DateUtil.getToday(), UpdateWidgetUIService.this);
			if (weatherInfo == null) {
				loadInfo();
			}
		} else {
			loadInfo();
		}
		Calendar cal = Calendar.getInstance();
		remoteViews.setTextViewText(R.id.widget_time,
				DateFormat.format("hh:mm", cal.getTime()));
		remoteViews.setTextViewText(
				R.id.widget_date,
				context.getResources().getString(R.string.weather_widget_date,
						StringUtils.hasText(cityName) ? cityName : "",
						DateUtil.WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1],
						DateFormat.format("MM月dd", cal.getTime())));
		if (weatherInfo == null) {
			remoteViews.setViewVisibility(R.id.weather_layout, View.GONE);
		} else {
			remoteViews.setViewVisibility(R.id.weather_layout, View.VISIBLE);
			getWeather();
			remoteViews.setTextViewText(R.id.tmp_text, weatherInfo.getNowTmp());
			remoteViews.setTextViewText(R.id.tmp_text_range, WeatherUtils
					.getTmpRange(weatherInfo, UpdateWidgetUIService.this));
			getPM2();
		}
		// 将AppWidgetProvider的子类包装成ComponentName对象
		ComponentName componentName = new ComponentName(context,
				WeatherWidgetProvider.class);
		// 调用AppWidgetManager将remoteViews添加到ComponentName中
		appWidgetManager.updateAppWidget(componentName, remoteViews);
	}

	private void loadInfo() {
		// 自动刷新数据
		String url = null;
		try {
			url = weatherDataService.getUrl(cityName,
					UpdateWidgetUIService.this);
		} catch (WeatherException e) {
		}
		weatherDataService.getWeatherInfo(url, UpdateWidgetUIService.this);
		weatherInfo = WeatherDataManager.getWeatherInfos(DateUtil.getToday(),
				UpdateWidgetUIService.this);
	}

	private void getPM2() {
		int pm25 = WeatherUtils.getPM2Level(weatherInfo);
		if (pm25 != 0) {
			int iconId = context.getResources()
					.getIdentifier("pm25_" + pm25 + "_w", "drawable",
							context.getPackageName());
			remoteViews.setImageViewResource(R.id.pm25_image, iconId);
			if (pm25 > 2) {
				remoteViews.setTextViewText(
						R.id.pm25_text,
						context.getResources().getString(R.string.pm25,
								weatherInfo.getAir().getAqigrade()));
			} else {
				remoteViews.setTextViewText(
						R.id.pm25_text,
						context.getResources().getString(R.string.pm25,
								weatherInfo.getAir().getAqigrade()));
			}
			remoteViews.setViewVisibility(R.id.pm25, View.VISIBLE);
		} else {
			remoteViews.setViewVisibility(R.id.pm25, View.GONE);
		}

	}

	private void getWeather() {
		int iconId = context.getResources().getIdentifier(
				"w" + weatherInfo.getIcon() + "_w", "drawable",
				context.getPackageName());
		remoteViews.setImageViewResource(R.id.sky_image, iconId);
		remoteViews.setTextViewText(R.id.sky_text, weatherInfo.getSky());
	}
}
