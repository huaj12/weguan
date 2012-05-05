package com.juzhai.search.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputSearchHotException extends JuzhaiException {

	private static final long serialVersionUID = -6603874415464902907L;

	public static final String SEARCH_HOT_NAME_IS_NULL = "170001";

	public static final String SEARCH_HOT_NAME_CITY_IS_EXIST = "170002";

	public InputSearchHotException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputSearchHotException(String errorCode) {
		super(errorCode);
	}
}
