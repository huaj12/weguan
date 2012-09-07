package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputIdeaException extends JuzhaiException {

	private static final long serialVersionUID = 6124172982633978282L;

	public static final String IDEA_CONTENT_DUPLICATE = "110001";

	public static final String IDEA_CONTENT_LENGTH_ERROR = "110002";

	public static final String IDEA_PLACE_LENGTH_ERROR = "110003";

	public static final String IDEA_CITY_IS_NULL = "110004";

	public static final String IDEA_CAN_NOT_DELETE = "110005";

	public static final String IDEA_CATEGORY_IS_NULL = "110006";

	public static final String IDEA_INTEREST_DUPLICATE = "110007";

	public InputIdeaException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputIdeaException(String errorCode) {
		super(errorCode);
	}

}
