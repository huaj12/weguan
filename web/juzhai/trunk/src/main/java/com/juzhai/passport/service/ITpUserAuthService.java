/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import com.juzhai.passport.bean.AuthInfo;

public interface ITpUserAuthService {

	/**
	 * 更新或者插入第三方用户授权信息
	 * 
	 * @param uid
	 * @param tpId
	 * @param authInfo
	 */
	void updateTpUserAuth(long uid, long tpId, AuthInfo authInfo);

	/**
	 * 缓存授权信息（仅试用于当前登录用户）
	 * 
	 * @param uid
	 * @param authInfo
	 */
	void cacheAuthInfo(long uid, AuthInfo authInfo);

	/**
	 * 获取AuthInfo
	 * 
	 * @param uid
	 * @param tpId
	 * @return
	 */
	AuthInfo getAuthInfo(long uid, long tpId);

	/**
	 * 获取小秘书的authinfo
	 * 
	 * @param tpName
	 * @return
	 */
	AuthInfo getSecretary(String tpName);

	/**
	 * 判断某用户是否登陆过该平台
	 * 
	 * @param uid
	 * @param tpId
	 * @return
	 */
	boolean isExist(long uid, long tpId);

	/**
	 * 获取某用户的所有授权信息
	 * 
	 * @param uid
	 * @return
	 */
	int countUserAuth(long uid);
}
