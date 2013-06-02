package com.easylife.weather.main.data;

import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.easylife.weather.BuildConfig;
import com.easylife.weather.core.ApplicationContext;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.model.Result.WeatherInfoResult;
import com.easylife.weather.core.utils.JacksonSerializer;
import com.easylife.weather.main.model.WeatherInfo;

public class WeatherDataManager {

	private static SharedPreferencesManager manager = null;

	private static Map<String, WeatherInfo> getWeatherInfos(Context context) {
		Map<String, WeatherInfo> datas = null;
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		datas = applicationContext.getWeatherInfos();
		if (CollectionUtils.isEmpty(datas)) {
			WeatherInfoResult result = getPersistWeatherInfo(context);
			if (result != null) {
				datas = result.getResult();
			}
		}
		return datas;
	}

	public static void delWeatherDate(Context context) {
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		applicationContext.setWeatherInfos(null);
		if (manager == null) {
			manager = new SharedPreferencesManager(context);
		}
		manager.delete(SharedPreferencesManager.P_WEATHERINFO);
	}

	public static int getBackgroundColor(Context context) {
		if (manager == null) {
			manager = new SharedPreferencesManager(context);
		}
		String color = "FF6666";
		int colorInt = Color.argb(255,
				Integer.parseInt(color.substring(0, 2), 16),
				Integer.parseInt(color.substring(2, 4), 16),
				Integer.parseInt(color.substring(4, 6), 16));
		return manager.getInt(SharedPreferencesManager.P_BACKGROUNDCOLOR,
				colorInt);
	}

	public static void setBackgroundColor(Context context, int color) {
		if (manager == null) {
			manager = new SharedPreferencesManager(context);
		}
		manager.commit(SharedPreferencesManager.P_BACKGROUNDCOLOR, color);
	}

	public static WeatherInfo getWeatherInfos(String date, Context context) {
		Map<String, WeatherInfo> datas = getWeatherInfos(context);
		if (CollectionUtils.isEmpty(datas)) {
			return null;
		}
		return datas.get(date);
	}

	private static WeatherInfoResult getPersistWeatherInfo(Context context) {
		if (manager == null) {
			manager = new SharedPreferencesManager(context);
		}
		String str = manager.getString(SharedPreferencesManager.P_WEATHERINFO);
		if (!StringUtils.hasText(str)) {
			return null;
		}
		WeatherInfoResult weahterinfos = null;
		try {
			weahterinfos = new ObjectMapper().readValue(str,
					WeatherInfoResult.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d("getPersistWeatherInfo", "json to weahterinfos is error",
						e);
			}
		}
		return weahterinfos;
	}

	public static void saveWeatherInfo(Context context, WeatherInfoResult datas) {
		if (!CollectionUtils.isEmpty(datas.getResult())) {
			ApplicationContext applicationContext = (ApplicationContext) context
					.getApplicationContext();
			applicationContext.setWeatherInfos(datas.getResult());
			if (manager == null) {
				manager = new SharedPreferencesManager(context);
			}
			try {
				String jsonStr = JacksonSerializer.toString(datas);
				manager.commit(SharedPreferencesManager.P_WEATHERINFO, jsonStr);
			} catch (JsonGenerationException e) {
				if (BuildConfig.DEBUG) {
					Log.d("saveWeatherInfo",
							"save weatherinfo JsonGenerationException", e);
				}
			}
		}
	}
}
