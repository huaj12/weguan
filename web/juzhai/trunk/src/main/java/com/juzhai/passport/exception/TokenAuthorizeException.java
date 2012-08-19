package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class TokenAuthorizeException extends JuzhaiException {

	private static final long serialVersionUID = -6251381067479251897L;
	/**
	 * 这个号不需要授权不是第三方用或者没有绑定邮箱
	 */
	public static final String USER_NOT_REQUIRE_AUTHORIZE = "230001";
	/**
	 * 一个平台只能绑定一款产品
	 */
	public static final String USER_BIND_TO_MORE = "230002";
	/**
	 * 重新授权的新号已经注册过了
	 */
	public static final String USER_IS_EXIST = "230003";

	public TokenAuthorizeException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public TokenAuthorizeException(String errorCode) {
		super(errorCode);
	}
}
