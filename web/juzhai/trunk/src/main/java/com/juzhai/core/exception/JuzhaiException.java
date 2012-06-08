package com.juzhai.core.exception;

public class JuzhaiException extends Exception {

	private static final long serialVersionUID = -463492418595191842L;

	public static final String SYSTEM_ERROR = "00001";

	public static final String ILLEGAL_OPERATION = "00002";

	public static final String USE_LOW_LEVEL = "00004";

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
