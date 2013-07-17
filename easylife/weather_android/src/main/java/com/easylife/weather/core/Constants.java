package com.easylife.weather.core;

public class Constants {
	public static final int DIANJIN_ID = 33066;
	public static final String SECRET_KEY = "790ef675dc75da16e50dd9773f132642";
	public static final int COLLING_TMP_MIN = 20;
	public static final int TEMPERATURE = 5;
	public static final long WEATHER_UPDATE_INTERVAL = 1000 * 3600 * 2;
	public static final int HOUR = 7;// 默认提醒时间
	public static final String ALARM_INTENT = "android.alarm.weather.action";
	public static final String UPDATE_DATA_INTENT = "android.weather.update.data.action";
	public static final String WIDGET_UPDATE_INTENT = "android.widget.update.weather.action";

	// 高温提示温度
	public static final int HOT_TMP = 35;
	// 推广框间隔时间
	public static final long INTERVAL_DIALOG_TIME = 1000 * 3600 * 24 * 3;
	// public static final long INTERVAL_DIALOG_TIME = 1000 * 60;
}
