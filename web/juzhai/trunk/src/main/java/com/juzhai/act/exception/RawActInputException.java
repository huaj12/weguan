package com.juzhai.act.exception;

import com.juzhai.core.exception.JuzhaiException;

public class RawActInputException extends JuzhaiException {
	private static final long serialVersionUID = -2615078451997679448L;


	/**
	 * raw_act项目时间不合法
	 */
	public static final String RAW_ACT_TIME_INVALID = "60001";
	
	/**
	 * raw_act id为空
	 */
	public static final String RAW_ACT_ID_IS_NULL = "60002";
	
	/**
	 * raw_act logo不合法
	 */
	public static final String RAW_ACT_LOGO_INVALID = "60003";
	
	/**
	 * raw_act 富文本图片url不合法
	 */
	public static final String RAW_ACT_DETAIL_LOGO_INVALID="60004";
	
	public RawActInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public RawActInputException(String errorCode) {
		super(errorCode);
	}

}
