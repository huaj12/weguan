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
}
