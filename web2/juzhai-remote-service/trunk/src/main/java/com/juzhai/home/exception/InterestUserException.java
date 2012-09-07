package com.juzhai.home.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InterestUserException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	/**
	 * 已经感兴趣
	 */
	public static final String INTEREST_USER_EXISTENCE = "40001";

	public InterestUserException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InterestUserException(String errorCode) {
		super(errorCode);
	}
}
