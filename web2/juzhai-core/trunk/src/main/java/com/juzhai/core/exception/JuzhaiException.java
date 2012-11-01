package com.juzhai.core.exception;

public class JuzhaiException extends Exception {

	private static final long serialVersionUID = -463492418595191842L;

	public static final String SYSTEM_ERROR = "00001";

	public static final String ILLEGAL_OPERATION = "00002";

	public static final String USE_LOW_LEVEL = "00004";
	/**
	 * 禁言
	 */
	public static final String AD_USER_GAG = "00005";
	/**
	 * 屏蔽
	 */
	public static final String AD_USER_SHIELD = "00006";
	/**
	 * 封ip
	 */
	public static final String AD_IP_SHIELD = "00007";

	/**
	 * 使用了非法字符
	 */
	public static final String ILLEGAL_CHARACTER = "00008";

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
