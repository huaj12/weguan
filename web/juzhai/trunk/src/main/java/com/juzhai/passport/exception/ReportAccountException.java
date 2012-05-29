package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class ReportAccountException extends JuzhaiException {

	private static final long serialVersionUID = -7758298879706824495L;

	/**
	 * 用户已被锁定
	 */
	public static final String USER_IS_SHIELD = "200001";

	private long shieldTime;

	public ReportAccountException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ReportAccountException(String errorCode, long shieldTime) {
		super(errorCode);
		this.shieldTime = shieldTime;
	}

	public ReportAccountException(String errorCode) {
		super(errorCode);
	}

	public long getShieldTime() {
		return shieldTime;
	}

	public void setShieldTime(long shieldTime) {
		this.shieldTime = shieldTime;
	}

}
