/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

public interface IProfileService {

	/**
	 * 缓存City属性
	 * 
	 * @param uid
	 */
	void cacheUserCity(long uid);

	/**
	 * 获取用户所在地城市
	 * 
	 * @param uid
	 * @return
	 */
	long getUserCityFromCache(long uid);
}
