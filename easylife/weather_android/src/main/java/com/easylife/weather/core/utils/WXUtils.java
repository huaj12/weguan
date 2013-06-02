package com.easylife.weather.core.utils;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXUtils {
	private final static String APP_ID = "wx1f00f076886b087b";
	private static IWXAPI api;

	public static IWXAPI getInstance(Context context) {
		if (api == null) {
			api = WXAPIFactory.createWXAPI(context, APP_ID, false);
			api.registerApp(APP_ID);
		}
		return api;
	}
}
