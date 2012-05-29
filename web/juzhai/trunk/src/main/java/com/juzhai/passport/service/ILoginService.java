/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.passport.exception.PassportAccountException;

public interface ILoginService {

	/**
	 * 本地登录
	 * 
	 * @param loginForm
	 * @return
	 * @throws LoginException
	 */
	long login(HttpServletRequest request, HttpServletResponse response,
			String loginName, String pwd, boolean persistent)
			throws PassportAccountException;

	/**
	 * 自动登录（比如：注册完自动登录）
	 * 
	 * @param request
	 * @param uid
	 */
	void autoLogin(HttpServletRequest request, HttpServletResponse response,
			long uid);

	/**
	 * 登录
	 * 
	 * @param request
	 * @param uid
	 */
	void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, RunType runType)
			throws PassportAccountException;

	/**
	 * 记住账号的登录
	 * 
	 * @param request
	 * @param response
	 * @throws PassportAccountException
	 */
	long persistentAutoLogin(HttpServletRequest request,
			HttpServletResponse response) throws PassportAccountException;

	/**
	 * cms登录
	 * 
	 * @param request
	 * @param uid
	 * @param tpId
	 * @param admin
	 */
	void cmsLogin(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId);

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
	void logout(HttpServletRequest request, HttpServletResponse response,
			long uid);

	/**
	 * 判断是否需要验证码
	 * 
	 * @param ip
	 * @return
	 */
	boolean useVerifyCode(String ip);

	/**
	 * 增加登录次数
	 * 
	 * @param ip
	 * @return
	 */
	long incrLoginCount(String ip);
}
