package com.juzhai.home.exception;

import com.juzhai.core.exception.JuzhaiException;

public class IndexException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	public IndexException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public IndexException(String errorCode) {
		super(errorCode);
	}
}
