package com.juzhai.act.exception;

import com.juzhai.core.exception.JuzhaiException;

public class DatingInputException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	/**
	 * 资料未完善
	 */
	public static final String DATING_PROFILE_SIMPLE = "30001";
	/**
	 * 已经在约中
	 */
	public static final String DATING_EXISTENCE = "30002";
	/**
	 * 本周已经达到限制
	 */
	public static final String DATING_WEEK_LIMIT = "30003";
	/**
	 * 达到总数限制
	 */
	public static final String DATING_TOTAL_LIMIT = "30004";
	/**
	 * 联系方式的值超过字数范围
	 */
	public static final String DATING_CONTACT_VALUE_INVALID = "30005";

	public DatingInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public DatingInputException(String errorCode) {
		super(errorCode);
	}
}
