package com.android.weatherspider.core;

import android.app.Application;

public class ApplicationContext extends Application {
	public final String DOMAIN = "10.10.10.103";
	// public final String baseUrl = "http://" + DOMAIN + ":8080/weather/";

	public final String baseUrl = "http://weather.51juzhai.com/";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public String getBaseUrl() {
		return baseUrl;
	}

}
