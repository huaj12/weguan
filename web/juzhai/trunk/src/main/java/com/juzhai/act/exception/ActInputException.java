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
	/**
	 * act detail超过长度限制
	 */
	public static final String ACT_DETAIL_IS_TOO_LONG = "10007";
	/**
	 * act detail不能为空
	 */
	public static final String ACT_DETAIL_IS_NULL = "10008";
	/**
	 * 全名长度不合法
	 */
	public static final String ACT_FULL_NAME_INVALID = "10009";
	/**
	 * 简介长度不合法
	 */
	public static final String ACT_INTRO_INVALID = "10010";
	/**
	 * 地址长度不合法
	 */
	public static final String ACT_ADDRESS_INVALID = "10011";

	public ActInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ActInputException(String errorCode) {
		super(errorCode);
	}
}
