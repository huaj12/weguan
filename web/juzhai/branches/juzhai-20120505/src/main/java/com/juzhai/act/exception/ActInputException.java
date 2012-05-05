package com.juzhai.act.exception;

import com.juzhai.core.exception.JuzhaiException;

public class ActInputException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	/**
	 * act名字被禁止
	 */
	public static final String ACT_NAME_FORBID = "11111110001";
	/**
	 * act名字未通过validation
	 */
	public static final String ACT_NAME_INVALID = "11111110002";
	/**
	 * act名字已经存在
	 */
	public static final String ACT_NAME_EXISTENCE = "11111110003";
	/**
	 * act uid或者name为空
	 */
	public static final String ACT_FIELD_ISNULL = "11111110004";
	/**
	 * act logo不合法
	 */
	public static final String ACT_LOGO_INVALID = "11111110005";
	/**
	 * act detail里面的图片地址不合法
	 */
	public static final String ACT_DETAIL_LOGO_INVALID = "11111110006";
	/**
	 * act detail超过长度限制
	 */
	public static final String ACT_DETAIL_IS_TOO_LONG = "11111110007";
	/**
	 * act detail不能为空
	 */
	public static final String ACT_DETAIL_IS_NULL = "11111110008";
	/**
	 * 全名长度不合法
	 */
	public static final String ACT_FULL_NAME_INVALID = "11111110009";
	/**
	 * 简介长度不合法
	 */
	public static final String ACT_INTRO_INVALID = "11111110010";
	/**
	 * 地址长度不合法
	 */
	public static final String ACT_ADDRESS_INVALID = "11111110011";

	public ActInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ActInputException(String errorCode) {
		super(errorCode);
	}
}
