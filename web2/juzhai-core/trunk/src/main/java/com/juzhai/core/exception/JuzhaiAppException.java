package com.juzhai.core.exception;

public class JuzhaiAppException extends Exception {

	private static final long serialVersionUID = -1288611300417707327L;
	public JuzhaiAppException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public JuzhaiAppException(String errorCode) {
		super(errorCode);
	}

	public JuzhaiAppException() {
		super();
	}

	public String getErrorCode() {
		return super.getMessage();
	}
}
