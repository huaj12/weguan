package com.juzhai.preference.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputUserPreferenceException extends JuzhaiException {

	private static final long serialVersionUID = -2095319146468436097L;
	/**
	 * 回答的内容长度过长
	 */
	public static final String PREFERENCE_ANSWER_IS_INVALID = "150004";

	/**
	 * 补充问题过长
	 */
	public static final String PREFERENCE_DESCRIPTION_IS_INVALID = "150005";

	public InputUserPreferenceException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputUserPreferenceException(String errorCode) {
		super(errorCode);
	}
}
