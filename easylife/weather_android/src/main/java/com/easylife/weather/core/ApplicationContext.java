package com.easylife.weather.core;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.baidu.location.LocationClient;
import com.easylife.weather.core.listenner.LocationListenner;
import com.easylife.weather.main.model.WeatherInfo;
import com.easylife.weather.passport.model.UserConfig;

public class ApplicationContext extends Application {
	public final String DOMAIN = "10.10.10.103";
	// public final String baseUrl = "http://" + DOMAIN + ":8080/weather/";
	private Map<String, WeatherInfo> weatherInfos = null;
	private UserConfig userConfig = null;
	public LocationClient mLocationClient = null;
	private Double latitude;
	private Double longitude;

	public final String baseUrl = "http://weather.51juzhai.com/";

	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(new LocationListenner(this,
				mLocationClient));
		super.onCreate();
	}

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			return null;
		}
		String version = packInfo.versionName;
		return version;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public Map<String, WeatherInfo> getWeatherInfos() {

		return weatherInfos;
	}

	public void setWeatherInfos(Map<String, WeatherInfo> weatherInfos) {
		this.weatherInfos = weatherInfos;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}

}
