package com.juzhai.platform.service;

import java.io.UnsupportedEncodingException;

import com.juzhai.passport.model.Thirdparty;

public interface IAuthorizeURLService {

	/**
	 * 授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String getAuthorizeURLforCode(Thirdparty tp, String turnTo)
			throws UnsupportedEncodingException;
}
