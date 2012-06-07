package com.juzhai.home.exception;

import com.juzhai.core.exception.JuzhaiException;

public class DialogException extends JuzhaiException {

	private static final long serialVersionUID = 5496832744344184736L;

	/**
	 * 私聊内容不合法
	 */
	public static final String DIALOG_CONTENT_INVALID = "90001";
	/**
	 * 存在禁止的词
	 */
	public static final String DIALOG_CONTENT_FORBID = "90002";

	/**
	 * 被对方屏蔽
	 */
	public static final String DIALOG_BLACKLIST_USER = "90003";

	public static final String DIALOG_USE_LOW_LEVEL = "90004";

	public DialogException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public DialogException(String errorCode) {
		super(errorCode);
	}
}
