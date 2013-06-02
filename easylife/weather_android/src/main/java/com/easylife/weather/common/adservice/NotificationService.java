package com.easylife.weather.common.adservice;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.util.StringUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.jpush.android.api.JPushInterface;

import com.easylife.weather.core.utils.WeatherUtils;
import com.easylife.weather.main.service.IWeatherDataService;
import com.easylife.weather.main.service.impl.WeatherDataService;
import com.easylife.weather.passport.data.UserConfigManager;
import com.easylife.weather.passport.model.UserConfig;
import com.easylife.weather.passport.service.IPassportService;
import com.easylife.weather.passport.service.impl.PassPortService;

public class NotificationService extends Service {
	private Timer updateWeatherInfoTimer;
	private long updateWeatherInfoPeriod = 2 * 3600 * 1000;

	private Timer updateUserTimer;
	private long updateUserPeriod = 6 * 3600 * 1000;
	private long updateUserBeginTime = 2 * 3600 * 1000;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);
		// 设置别名
		JPushInterface.setAliasAndTags(NotificationService.this,
				WeatherUtils.getDeviceToken(NotificationService.this), null);
		// 2小时更新一次天气数据
		updateWeatherInfoTimer = new Timer();
		updateWeatherInfoTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					String cityName = UserConfigManager
							.getCityName(NotificationService.this);
					if (StringUtils.hasText(cityName)) {
						IWeatherDataService weatherDataService = new WeatherDataService();
						weatherDataService.updateWeatherDate(cityName,
								NotificationService.this);
					}
				} catch (Exception e) {

				}
			}
		}, 0, updateWeatherInfoPeriod);
		// 6小时和服务器同步一次配置数据
		updateUserTimer = new Timer();
		updateUserTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					UserConfig user = UserConfigManager
							.getUserConfig(NotificationService.this);
					if (user != null && StringUtils.hasText(user.getCityName())) {
						IPassportService passportService = new PassPortService();
						passportService.updateUserConfig(UserConfigManager
								.getUserConfig(NotificationService.this),
								NotificationService.this);
					}
				} catch (Exception e) {

				}
			}
		}, updateUserBeginTime, updateUserPeriod);
	}

	@Override
	public void onDestroy() {
		if (updateWeatherInfoTimer != null) {
			updateWeatherInfoTimer.cancel();
		}
		if (updateUserTimer != null) {
			updateUserTimer.cancel();
		}
		super.onDestroy();

	}

}
