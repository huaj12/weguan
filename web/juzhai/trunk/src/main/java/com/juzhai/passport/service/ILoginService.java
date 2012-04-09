/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import javax.servlet.http.HttpServletRequest;

import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.passport.exception.LoginException;

public interface ILoginService {

	/**
	 * 登录
	 * 
	 * @param request
	 * @param uid
	 */
	void login(HttpServletRequest request, long uid, long tpId, RunType runType)
			throws LoginException;

	/**
	 * cms登录
	 * 
	 * @param request
	 * @param uid
	 * @param tpId
	 * @param admin
	 */
	void cmsLogin(HttpServletRequest request, long uid, long tpId, boolean admin);

	/**
	 * 用户是否在线
	 * 
	 * @param uid
	 * @return
	 */
	boolean isOnline(long uid);

	/**
	 * 更新在线状态
	 * 
	 * @param uid
	 */
	void updateOnlineState(long uid);

	/**
	 * 下线
	 * 
	 * @param uid
	 */
	void logout(HttpServletRequest request, long uid);
}
