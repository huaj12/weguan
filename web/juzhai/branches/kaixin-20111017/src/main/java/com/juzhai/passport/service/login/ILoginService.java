/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import javax.servlet.http.HttpServletRequest;

public interface ILoginService {

	/**
	 * 登录
	 * 
	 * @param request
	 * @param uid
	 */
	void login(HttpServletRequest request, long uid, long tpId);

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
	 * 判断是否第一次登陆并删除标记
	 * 
	 * @param request
	 * @return
	 */
	boolean isDayFirstLoginAndDel(HttpServletRequest request);
}
