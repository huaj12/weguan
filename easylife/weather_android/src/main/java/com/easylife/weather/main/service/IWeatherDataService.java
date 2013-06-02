package com.easylife.weather.main.service;

import android.content.Context;

import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.model.Result.WeatherInfoResult;

public interface IWeatherDataService {
	/**
	 * 获取天气数据
	 * 
	 * @param url
	 * @param context
	 * @return
	 */
	WeatherInfoResult getWeatherInfo(String url, Context context);

	/**
	 * 通过坐标获取城市天气数据url
	 * 
	 * @param lat
	 * @param lng
	 * @return
	 */
	String getUrl(double lat, double lng, Context context)
			throws WeatherException;

	/**
	 * 根据城市名称获取天气数据url
	 * 
	 * @param citiName
	 * @param context
	 * @return
	 */
	String getUrl(String cityName, Context context) throws WeatherException;

	/**
	 * 更新天气数据
	 * 
	 * @param cityName
	 * @param contextc
	 */
	void updateWeatherDate(String cityName, Context contextc);
}
