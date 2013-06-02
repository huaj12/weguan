package com.easylife.weather.passport.service;

import android.content.Context;

import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.passport.model.UserConfig;

public interface IPassportService {
	/**
	 * 注册新用户初始化配置信息
	 * 
	 * @param context
	 * @return
	 */
	UserConfig register(Context context) throws WeatherException;

	/**
	 * 注册新用户带城市
	 * 
	 * @param cityname
	 * @param context
	 * @return
	 */
	UserConfig register(String cityname, Context context)
			throws WeatherException;

	/**
	 * 更新用户配置信息
	 * 
	 * @param userConfig
	 * @param context
	 */
	void updateUserConfig(UserConfig userConfig, Context context)
			throws WeatherException;
}
