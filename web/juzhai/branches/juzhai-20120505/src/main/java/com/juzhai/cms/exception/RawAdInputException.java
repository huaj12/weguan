package com.juzhai.cms.exception;

import com.juzhai.core.exception.JuzhaiException;

public class RawAdInputException extends JuzhaiException {
	private static final long serialVersionUID = -2615078451997679448L;
	/**
	 * 导入文件不能为空
	 */
	public static final String RAW_AD_FILE_IS_NULL = "70001";
	
	/**
	 * 读取文件错误
	 */
	public static final String RAW_AD_READ_FILE_IS_INVALID = "70002";
	
	/**
	 * 文件内容为空或者文件格式错误
	 */
	public static final String RAW_AD_FILE_CONTENT_INVALID = "70003";
	
	public static final String RAW_AD_REMOVE_ID_IS_NULL = "70004";
	
	public RawAdInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public RawAdInputException(String errorCode) {
		super(errorCode);
	}

}
