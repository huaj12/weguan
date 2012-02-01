package com.juzhai.act.exception;

import com.juzhai.core.exception.JuzhaiException;

public class ActAdInputException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	/**
	 * 创建act_ad失败
	 */
	public static final String CREATE_ACT_AD_IS_ERROR = "80001";
	/**
	 * rawadid不存在
	 */

	public static final String ACT_AD_RAW_AD_ID_IS_NULL = "80002";

	/**
	 * act name不存在
	 */
	public static final String ACT_AD_NAME_IS_NOT_EXIST = "80003";

	/**
	 * 该项目插入过该优惠信息
	 */
	public static final String ACT_AD_URL_IS_EXIST = "80004";

	/**
	 * 删除的广告优惠信息不存在
	 */
	public static final String REMOVE_ACT_AD_IS_NOT_EXIST = "80005";

	/**
	 * 删除act_ad失败
	 */
	public static final String REMOVE_ACT_AD_IS_ERROR = "80006";

	public ActAdInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ActAdInputException(String errorCode) {
		super(errorCode);
	}
}
