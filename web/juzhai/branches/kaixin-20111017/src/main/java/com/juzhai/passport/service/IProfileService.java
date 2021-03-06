/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import java.util.List;

import com.juzhai.passport.bean.ProfileCache;
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
	ProfileCache cacheProfile(Profile profile, String tpIdentity);

	/**
	 * 缓存ProfileCache
	 * 
	 * @param uid
	 * @param profileCache
	 */
	void cacheProfileCache(long uid, ProfileCache profileCache);

	/**
	 * 判断两个人是否可能同城，当其中有人没有所在地信息，即认为是同城
	 * 
	 * @param uid1
	 * @param uid2
	 * @return 0.不是同城 1.有可能同城 2.同城
	 */
	int isMaybeSameCity(long uid1, long uid2);

	/**
	 * 获取订阅邮箱的用户资料对象
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Profile> getSubEmailProfiles(int firstResult, int maxResults);

	/**
	 * 取用户密钥，当不存在，则生成一个并保存
	 * 
	 * @param uid
	 */
	byte[] getUserSecretKey(long uid);
}
