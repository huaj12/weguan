package com.juzhai.android.home.service;

import android.content.Context;

public interface IHomeService {

	/**
	 * 刷新用户信息
	 * 
	 * @param context
	 * @return 错误
	 */
	String refresh(Context context);

	/**
	 * 更新所在位置
	 * 
	 * @param context
	 * @param longitude
	 * @param latitude
	 */
	void updateLocation(Context context, double longitude, double latitude);

}
