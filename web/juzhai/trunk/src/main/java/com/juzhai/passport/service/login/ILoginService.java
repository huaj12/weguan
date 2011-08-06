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
}
