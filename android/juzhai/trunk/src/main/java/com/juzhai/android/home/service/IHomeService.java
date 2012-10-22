package com.juzhai.android.home.service;

import android.content.Context;

import com.juzhai.android.home.exception.HomeException;
import com.juzhai.android.passport.model.User;

public interface IHomeService {

	/**
	 * 刷新用户信息
	 * 
	 * @param context
	 * @return 错误
	 */
	String refresh(Context context);

	/**
	 * 获取用户uid
	 * 
	 * @param context
	 * @param uid
	 * @return 错误
	 * @throws HomeException
	 */
	User getUserInfo(Context context, long uid) throws HomeException;

	/**
	 * 更新所在位置
	 * 
	 * @param context
	 * @param longitude
	 * @param latitude
	 */
	void updateLocation(Context context, double longitude, double latitude);

}
