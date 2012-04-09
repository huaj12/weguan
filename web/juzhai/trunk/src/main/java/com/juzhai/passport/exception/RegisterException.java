package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class RegisterException extends JuzhaiException {

	private static final long serialVersionUID = 3604695181375854387L;

	/**
	 * 邮箱账号不合法
	 */
	public static final String EMAIL_ACCOUNT_INVALID = "10001";

	/**
	 * 密码长度不对
	 */
	public static final String PWD_LENGTH_ERROR = "10002";

	/**
	 * 确认密码输入不一致
	 */
	public static final String CONFIRM_PWD_ERROR = "10003";

	public RegisterException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public RegisterException(String errorCode) {
		super(errorCode);
	}
}
