package com.easylife.weather.passport.data;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.util.Log;

import com.easylife.weather.BuildConfig;
import com.easylife.weather.core.ApplicationContext;
import com.easylife.weather.core.data.SharedPreferencesManager;
import com.easylife.weather.core.utils.JacksonSerializer;
import com.easylife.weather.passport.model.UserConfig;

public class UserConfigManager {
	private static SharedPreferencesManager manager = null;

	public static void saveUserConfig(UserConfig userConfig, Context context) {
		if (userConfig != null) {
			if (manager == null) {
				manager = new SharedPreferencesManager(context);
			}
			ApplicationContext applicationContext = (ApplicationContext) context
					.getApplicationContext();
			applicationContext.setUserConfig(userConfig);
			try {
				String jsonUserStr = JacksonSerializer.toString(userConfig);
				manager.commit(SharedPreferencesManager.P_USER_CONFIG,
						jsonUserStr);
			} catch (JsonGenerationException e) {
				if (BuildConfig.DEBUG) {
					Log.d("saveUserConfig",
							"save saveUserConfig JsonGenerationException", e);
				}
			}
		}
	}

	public static UserConfig getUserConfig(Context context) {
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		UserConfig userConfig = applicationContext.getUserConfig();
		if (userConfig == null) {
			userConfig = getPersistUserConfig(context);
			if (userConfig != null) {
				applicationContext.setUserConfig(userConfig);
			}
		}
		return userConfig;
	}

	private static UserConfig getPersistUserConfig(Context context) {
		if (manager == null) {
			manager = new SharedPreferencesManager(context);
		}
		String str = manager.getString(SharedPreferencesManager.P_USER_CONFIG);
		if (!StringUtils.hasText(str)) {
			return null;
		}
		UserConfig config = null;
		try {
			config = new ObjectMapper().readValue(str, UserConfig.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d("getPersistUserConfig", "json to userconfig is error", e);
			}
		}
		return config;
	}

	public static String getCityName(Context context) {
		if (getUserConfig(context) == null) {
			return null;
		}
		return getUserConfig(context).getCityName();
	}
}
