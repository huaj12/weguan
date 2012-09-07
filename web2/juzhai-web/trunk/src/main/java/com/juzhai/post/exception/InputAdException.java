package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputAdException extends JuzhaiException {

	private static final long serialVersionUID = -2537856278502350898L;
	
	public static final String AD_IS_EXIST = "110002";
	
	public InputAdException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputAdException(String errorCode) {
		super(errorCode);
	}

}
