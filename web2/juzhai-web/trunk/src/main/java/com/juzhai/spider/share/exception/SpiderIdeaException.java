package com.juzhai.spider.share.exception;

import com.juzhai.core.exception.JuzhaiException;

public class SpiderIdeaException extends JuzhaiException {

	private static final long serialVersionUID = 8755191427159988340L;

	public static final String SPIDER_IDEA_URL_IS_ERROR = "190000";

	public static final String SPIDER_IDEA_URL_IS_NOT_EXIST = "190001";

	public static final String SPIDER_IDEA_IMAGE_IS_ERROR = "190002";

	public static final String SPIDER_IDEA_URL_IS_NULL = "190003";

	public static final String SPIDER_IDEA_TIME_OUT = "190004";

	public static final String SPIDER_IDEA_TO_MORE = "190005";

	public SpiderIdeaException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public SpiderIdeaException(String errorCode) {
		super(errorCode);
	}
}
