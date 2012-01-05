package com.juzhai.platform.service;

import com.juzhai.passport.model.Thirdparty;

public interface IAuthorizeURLService {

	/**
	 * 授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 */
	String getAuthorizeURLforCode(Thirdparty tp, String turnTo);
}
