package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputRawIdeaException extends JuzhaiException {

	private static final long serialVersionUID = -6751285711866491995L;

	public static final String RAW_IDEA_CONTENT_LENGTH_ERROR = "180001";

	public static final String RAW_IDEA_CATEGORYID_IS_NULL = "180002";

	public static final String RAW_IDEA_PIC_IS_NULL = "180003";

	public static final String RAW_IDEA_TIME_IS_NULL = "180004";

	public static final String RAW_IDEA_ADDRESS_IS_NULL = "180005";

	public static final String RAW_IDEA_DETAIL_IS_NULL = "180006";

	public static final String RAW_IDEA_ADDRESS_TOO_LONG = "180007";

	public static final String RAW_IDEA_DETAIL_TOO_LONG = "180008";

	public static final String RAW_IDEA_LINK_TOO_LONG = "180009";

	public static final String RAW_IDEA_CONTENT_EXIST = "180010";

	public static final String RAW_IDEA_CITY_IS_NULL = "180011";

	public static final String RAW_IDEA_TOWN_IS_NULL = "180012";

	public static final String RAW_IDEA_TIME_IS_ERROR = "180013";

	public static final String RAW_IDEA_CREATE_TO_MORE = "180014";

	public InputRawIdeaException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputRawIdeaException(String errorCode) {
		super(errorCode);
	}
}
