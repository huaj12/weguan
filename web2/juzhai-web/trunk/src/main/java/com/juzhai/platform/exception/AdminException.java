package com.juzhai.platform.exception;

import com.juzhai.core.exception.JuzhaiException;

public class AdminException extends JuzhaiException {

	private static final long serialVersionUID = -6362400103928880519L;
	/**
	 * 第三方api调用达到上限
	 */
	public static final String ADMIN_API_EXCEED_LIMIT = "130001";

	public AdminException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public AdminException(String errorCode) {
		super(errorCode);
	}

}
