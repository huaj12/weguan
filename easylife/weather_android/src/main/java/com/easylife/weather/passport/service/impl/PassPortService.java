package com.easylife.weather.passport.service.impl;

import android.content.Context;

import com.easylife.weather.core.Constants;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.easylife.weather.passport.service.IPassportService;

public class PassPortService implements IPassportService {
	private final String registerUri = "passport/saveUserConfig";

	@Override
	public UserConfig register(Context context) throws WeatherException {
		return register(null, context);
	}

	@Override
	public UserConfig register(String cityname, Context context)
			throws WeatherException {
		UserConfig userConfig = new UserConfig();
		userConfig.setCityName(cityname);
		userConfig.setHourStr("7:00");
		userConfig.setHour(Constants.HOUR);
		userConfig.setRemindCooling(false);
		userConfig.setRemindRain(true);
		userConfig.setRemindWind(false);
		userConfig.setToken(WeatherUtils.getDeviceToken(context));
		UserConfigManager.saveUserConfig(userConfig, context);
		return userConfig;
	}

	@Override
	public void updateUserConfig(UserConfig userConfig, Context context)
			throws WeatherException {
		UserConfigManager.saveUserConfig(userConfig, context);
	}

}
