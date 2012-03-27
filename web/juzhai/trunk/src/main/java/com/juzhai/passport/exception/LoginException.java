package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class LoginException extends JuzhaiException {
	private long shieldTime;
	private static final long serialVersionUID = -3029162160532668772L;
	/**
	 * 用户已被锁定
	 */
	public static final String USER_IS_SHIELD = "160001";

	public LoginException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public LoginException(String errorCode, long shieldTime) {
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
