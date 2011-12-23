package com.juzhai.act.exception;

import com.juzhai.core.exception.JuzhaiException;

public class ActInputException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	/**
	 * act名字被禁止
	 */
	public static final String ACT_NAME_FORBID = "10001";
	/**
	 * act名字未通过validation
	 */
	public static final String ACT_NAME_INVALID = "10002";
	/**
	 * act名字已经存在
	 */
	public static final String ACT_NAME_EXISTENCE = "10003";
	/**
	 * act uid或者name为空
	 */
	public static final String ACT_FIELD_ISNULL = "10004";
	/**
	 * act logo不合法
	 */
	public static final String ACT_LOGO_INVALID = "10005";
	/**
	 * act detail里面的图片地址不合法
	 */
	public static final String ACT_DETAIL_LOGO_INVALID = "10006";
	

	public ActInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ActInputException(String errorCode) {
		super(errorCode);
	}
}
