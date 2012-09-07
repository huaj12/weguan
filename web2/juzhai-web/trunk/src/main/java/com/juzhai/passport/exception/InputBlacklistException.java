package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputBlacklistException extends JuzhaiException {

	private static final long serialVersionUID = 3169514988529091146L;

	public InputBlacklistException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputBlacklistException(String errorCode) {
		super(errorCode);
	}
}
