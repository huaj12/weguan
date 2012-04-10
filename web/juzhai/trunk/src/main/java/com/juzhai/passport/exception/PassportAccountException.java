package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class PassportAccountException extends JuzhaiException {

	private static final long serialVersionUID = -3029162160532668772L;

	private long shieldTime;

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

	/**
	 * 账号或密码错误
	 */
	public static final String ACCOUNT_OR_PWD_ERROR = "10004";

	/**
	 * 用户已被锁定
	 */
	public static final String USER_IS_SHIELD = "10005";

	/**
	 * 密码错误
	 */
	public static final String PWD_ERROR = "10006";

	/**
	 * 验证码错误
	 */
	public static final String VERIFY_CODE_ERROR = "10007";

	/**
	 * 激活码错误
	 */
	public static final String ACTIVE_CODE_ERROR = "10008";

	public PassportAccountException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public PassportAccountException(String errorCode) {
		super(errorCode);
	}

	public PassportAccountException(String errorCode, long shieldTime) {
		super(errorCode);
		this.shieldTime = shieldTime;
	}

	public long getShieldTime() {
		return shieldTime;
	}

	public void setShieldTime(long shieldTime) {
		this.shieldTime = shieldTime;
	}

}
