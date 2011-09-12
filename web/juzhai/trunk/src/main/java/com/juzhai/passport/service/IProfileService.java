/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import java.util.List;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;

public interface IProfileService {

	/**
	 * 缓存City属性
	 * 
	 * @param uid
	 */
	void cacheUserCity(long uid);

	/**
	 * 缓存City属性
	 * 
	 * @param uid
	 */
	void cacheUserCity(Profile profile);

	/**
	 * 获取用户所在地城市
	 * 
	 * @param uid
	 * @return
	 */
	long getUserCityFromCache(long uid);

	/**
	 * 根据城市，找出所有在这个城市中的人
	 * 
	 * @param cityId
	 * @return
	 */
	List<Profile> getProfilesByCityId(long cityId);

	/**
	 * 根据用户ID获取Profile，先查缓存
	 * 
	 * @param uid
	 * @return
	 */
	ProfileCache getProfileCacheByUid(long uid);

	/**
	 * 缓存profile
	 * 
	 * @param profile
	 */
	ProfileCache cacheProfile(Profile profile);

	/**
	 * 订阅邮箱
	 * 
	 * @param uid
	 * @param email
	 * @return
	 * @throws ProfileInputException
	 */
	boolean subEmail(long uid, String email) throws ProfileInputException;
}
