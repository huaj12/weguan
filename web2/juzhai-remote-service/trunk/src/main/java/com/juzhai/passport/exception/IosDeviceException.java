package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class IosDeviceException extends JuzhaiException {

	private static final long serialVersionUID = -5910294849012067406L;
	/**
	 * 设备名不能为空
	 */
	public static final String IOS_DEVICE_TOKEN_IS_NULL = "240001";

	public IosDeviceException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public IosDeviceException(String errorCode) {
		super(errorCode);
	}
}