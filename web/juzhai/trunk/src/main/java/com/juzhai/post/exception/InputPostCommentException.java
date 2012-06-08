package com.juzhai.post.exception;

import com.juzhai.core.exception.JuzhaiException;

public class InputPostCommentException extends JuzhaiException {

	private static final long serialVersionUID = 460097221232116803L;

	/**
	 * 拒宅内容长度错误
	 */
	public static final String COMMENT_CONTENT_LENGTH_ERROR = "140001";

	/**
	 * 拒宅内容使用了屏蔽词
	 */
	public static final String COMMENT_CONTENT_FORBID = "140002";

	/**
	 * 拒宅内容重复
	 */
	public static final String COMMENT_CONTENT_DUPLICATE = "140003";

	/**
	 * 拒宅id不存在
	 */
	public static final String POST_ID_NOT_EXIST = "140004";

	/**
	 * 被黑名单
	 */
	public static final String COMMENT_BLACKLIST_USER = "140005";
	/**
	 * 使用级别不够
	 */
	//TODO (review) 在父类里定义这个错误，是通用的。使用级别不够，目前报错信息可写“先验证邮箱”
	public static final String COMMENT_USE_LOW_LEVEL = "140006";

	public InputPostCommentException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public InputPostCommentException(String errorCode) {
		super(errorCode);
	}
}
