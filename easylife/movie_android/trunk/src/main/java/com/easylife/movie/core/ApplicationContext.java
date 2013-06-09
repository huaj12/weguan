package com.easylife.movie.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ApplicationContext extends Application {
	public final String DOMAIN = "10.10.10.103";

	// public final String baseUrl = "http://" + DOMAIN + ":8080/easylife/";

	public final String baseUrl = "http://easylife.51juzhai.com/";

	@Override
	public void onCreate() {
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

}
