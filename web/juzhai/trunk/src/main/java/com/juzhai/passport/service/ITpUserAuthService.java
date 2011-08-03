/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import javax.servlet.http.HttpServletRequest;

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
	 * 当前仅当单机情况下，并且在本tomcat下才能使用此方法
	 * 
	 * @param request
	 * @param authInfo
	 */
	void saveAuthInfoToSession(HttpServletRequest request, AuthInfo authInfo);
}
