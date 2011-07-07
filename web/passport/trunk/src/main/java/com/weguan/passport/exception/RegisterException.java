package com.weguan.passport.exception;

public class RegisterException extends WeguanException {

	private static final long serialVersionUID = -2168895320219757711L;

	/**
	 * 注册非法输入
	 */
	public static final String REGISTER_INVALID = "20001";
	/**
	 * 注册用户名非法
	 */
	public static final String REGISTER_USERNAME_INVALID = "20002";
	/**
	 * 注册邮箱非法
	 */
	public static final String REGISTER_EMAIL_INVALID = "20003";
	/**
	 * 注册密码非法
	 */
	public static final String REGISTER_PASSWORD_INVALID = "20004";

	public RegisterException(String errorCode) {
		super(errorCode);
	}

	public RegisterException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
