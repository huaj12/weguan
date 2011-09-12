package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class ProfileInputException extends JuzhaiException {

	private static final long serialVersionUID = 4890872155656488515L;

	/**
	 * 邮箱非法
	 */
	public static final String PROFILE_EMAIL_INVALID = "20001";

	public ProfileInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ProfileInputException(String errorCode) {
		super(errorCode);
	}
}
