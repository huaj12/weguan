package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputReportException extends JuzhaiException {

	private static final long serialVersionUID = 5594504098824774962L;
	/**
	 * 补充说明不能大于200个字
	 */
	public static final String REPORT_DESCRIPTION_TOO_LONG = "160001";

	public InputReportException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputReportException(String errorCode) {
		super(errorCode);
	}
}
