package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputPostException extends JuzhaiException {

	private static final long serialVersionUID = 460097221232116803L;

	/**
	 * idea已经发布过了
	 */
	public static final String IDEA_HAS_POSTED = "100001";

	/**
	 * 拒宅内容长度错误
	 */
	public static final String POST_CONTENT_LENGTH_ERROR = "100002";

	/**
	 * 拒宅内容使用了屏蔽词
	 */
	public static final String POST_CONTENT_FORBID = "100003";

	/**
	 * 拒宅内容重复
	 */
	public static final String POST_CONTENT_DUPLICATE = "100004";

	/**
	 * 拒宅地点长度错误
	 */
	public static final String POST_PLACE_LENGTH_ERROR = "100005";

	/**
	 * 发布拒宅太频繁
	 */
	public static final String POST_TOO_FREQUENT = "100006";

	public InputPostException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputPostException(String errorCode) {
		super(errorCode);
	}
}
