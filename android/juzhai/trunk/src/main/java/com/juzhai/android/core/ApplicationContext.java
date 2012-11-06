package com.juzhai.android.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.passport.data.UserCache;

public class ApplicationContext extends Application {
	private final UserCache userCache = new UserCache();
	public final String DOMAIN = "192.168.15.103";

	// public static final String BASEURL =
	// "http://192.168.15.104:8080/mobile/";

	public final String baseUrl = "http://m.51juzhai.com/";

	// public final String baseUrl = "http://" + DOMAIN + ":8080/mobile/";

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			if (BuildConfig.DEBUG) {
				Log.d(ApplicationContext.class.getSimpleName(),
						"get packInfo error");
			}
			return null;
		}
		String version = packInfo.versionName;
		return version;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public UserCache getUserCache() {
		return userCache;
	}

}
