package com.juzhai.preference.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputPreferenceException extends JuzhaiException {

	private static final long serialVersionUID = -2095319146468436097L;
	/**
	 * 偏好名称不能为空
	 */
	public static final String PREFERENCE_NAME_IS_NULL = "150001";
	/**
	 * 偏好名称必须在1-100字之间
	 */
	public static final String PREFERENCE_NAME_IS_INVALID = "150002";
	/**
	 * 请选择用户输入框类型
	 */
	public static final String PREFERENCE_INPUT_IS_NULL = "150003";

	public InputPreferenceException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputPreferenceException(String errorCode) {
		super(errorCode);
	}
}
