package com.weguan.passport.exception;

public class WeguanException extends Exception {

	private static final long serialVersionUID = -463492418595191842L;

	public WeguanException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public WeguanException(String errorCode) {
		super(errorCode);
	}

	public WeguanException() {
		super();
	}

	public String getErrorCode() {
		return super.getMessage();
	}
}
