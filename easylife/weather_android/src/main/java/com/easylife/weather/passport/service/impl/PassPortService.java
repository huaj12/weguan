package com.easylife.weather.passport.service.impl;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import android.content.Context;

import com.easylife.weather.R;
import com.easylife.weather.core.exception.WeatherException;
import com.easylife.weather.core.model.Result.UserResult;
import com.easylife.weather.core.utils.HttpUtils;
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
		userConfig.setRemindCooling(false);
		userConfig.setRemindRain(true);
		userConfig.setRemindWind(false);
		userConfig.setToken(WeatherUtils.getDeviceToken(context));
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("token", userConfig.getToken());
		values.put("remindRain", userConfig.isRemindRain());
		values.put("remindWind", userConfig.isRemindWind());
		values.put("remindCooling", userConfig.isRemindCooling());
		values.put("hour", userConfig.getHourStr());
		if (StringUtils.hasText(userConfig.getCityName())) {
			values.put("cityName", userConfig.getCityName());
		}
		UserConfigManager.saveUserConfig(userConfig, context);
		try {
			HttpUtils.post(context, registerUri, values, UserResult.class);
		} catch (RestClientException e) {
			throw new WeatherException(context, R.string.no_network);
		}

		return userConfig;
	}

	@Override
	public void updateUserConfig(UserConfig userConfig, Context context)
			throws WeatherException {
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("token", userConfig.getToken());
		values.put("remindRain", userConfig.isRemindRain());
		values.put("remindWind", userConfig.isRemindWind());
		values.put("remindCooling", userConfig.isRemindCooling());
		values.put("hour", userConfig.getHourStr());
		values.put("cityName", userConfig.getCityName());
		UserConfigManager.saveUserConfig(userConfig, context);
		try {
			ResponseEntity<UserResult> entity = HttpUtils.post(context,
					registerUri, values, UserResult.class);
		} catch (RestClientException e) {
			throw new WeatherException(context, R.string.no_network);
		}
	}

}
