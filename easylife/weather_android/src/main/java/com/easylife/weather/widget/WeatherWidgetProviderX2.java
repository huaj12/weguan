package com.easylife.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.easylife.weather.R;
import com.easylife.weather.core.stat.UmengEvent;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.activity.LaunchActivity;
import com.easylife.weather.widget.service.UpdateWidgetUIServiceX2;
import com.umeng.analytics.MobclickAgent;

public class WeatherWidgetProviderX2 extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		startService(context);
		Log.e("appwidget", "--update--");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			startService(context);
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Intent intent = new Intent(context, UpdateWidgetUIServiceX2.class);
		context.stopService(intent);
		WeatherUtils.cancelUpdateData(context);
	}

	@Override
	public void onEnabled(Context context) {
		try {
			MobclickAgent.onEvent(context, UmengEvent.ADD_WEATHER_WIDGET);
		} catch (Exception e) {

		}
		super.onEnabled(context);
	}

	public void startService(Context context) {
		WeatherUtils.updateData(context);
		// 创建RemoteViews对象
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.widget_layoutx2);
		UpdateWidgetUIServiceX2.appWidgetManager = AppWidgetManager
				.getInstance(context);
		UpdateWidgetUIServiceX2.context = context;
		UpdateWidgetUIServiceX2.remoteViews = views;
		Intent fullIntent = new Intent(context, LaunchActivity.class);
		PendingIntent Pfullintent = PendingIntent.getActivity(context, 0,
				fullIntent, 0);
		views.setOnClickPendingIntent(R.id.widget_layout, Pfullintent);
		// 启动刷新UI的Service
		Intent intent = new Intent(context, UpdateWidgetUIServiceX2.class);
		context.startService(intent);

	}
}