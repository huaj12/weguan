package com.juzhai.core.exception;

public class JuzhaiException extends Exception {

	private static final long serialVersionUID = -463492418595191842L;

	public JuzhaiException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public JuzhaiException(String errorCode) {
		super(errorCode);
	}

	public JuzhaiException() {
		super();
	}

	public String getErrorCode() {
		return super.getMessage();
	}
}
