package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputRecommendException extends JuzhaiException {

	private static final long serialVersionUID = 8688785818627587960L;

	public InputRecommendException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputRecommendException(String errorCode) {
		super(errorCode);
	}

	public static final String ADD_IDEA_ID_NOT_EXIST = "220001";

	public static final String ADD_IDEA_IS_TOO_MORE = "220002";
}
