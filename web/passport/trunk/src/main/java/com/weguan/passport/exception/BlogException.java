package com.weguan.passport.exception;

public class BlogException extends WeguanException {

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
	/**
	 * 用户名存在
	 */
	public static final String REGISTER_USERNAME_EXISTENCE = "20005";
	/**
	 * 确认密码错误
	 */
	public static final String REGISTER_PASSWORD_CONFIRM_ERROR = "20006";
	/**
	 * 确认密码错误
	 */
	public static final String REGISTER_OLD_PASSWORD_ERROR = "20007";

	public BlogException(String errorCode) {
		super(errorCode);
	}

	public BlogException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
