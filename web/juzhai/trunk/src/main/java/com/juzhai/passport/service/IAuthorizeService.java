/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.service;

import javax.servlet.http.HttpServletRequest;

import com.juzhai.passport.exception.TokenAuthorizeException;

public interface IAuthorizeService {
	void tokenAuthorize(HttpServletRequest request, long uid, long userTpId,
			long tpId) throws TokenAuthorizeException;
}
