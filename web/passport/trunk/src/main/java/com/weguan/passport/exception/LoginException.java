package com.weguan.passport.exception;

public class LoginException extends WeguanException {

	private static final long serialVersionUID = 5219202870566470487L;

	/**
	 * 登录信息非法
	 */
	public static final String LOGIN_INVALID = "10001";
	/**
	 * 用户名不存在
	 */
	public static final String LOGIN_NAME_INEXISTENCE = "10002";
	/**
	 * 密码错误
	 */
	public static final String LOGIN_PASSWORD_ERROR = "10003";

	public LoginException(String errorCode) {
		super(errorCode);
	}

	public LoginException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
