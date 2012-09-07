package com.juzhai.platform.exception;

import com.juzhai.core.exception.JuzhaiException;

public class TokenAuthorizeException extends JuzhaiException {

	private static final long serialVersionUID = -6251381067479251897L;
	/**
	 * 这个号不需要授权不是第三方用或者没有绑定邮箱
	 */
	public static final String USER_NOT_REQUIRE_AUTHORIZE = "230001";
	/**
	 * 一个平台绑定了多个产品。不能切换新号授权只能用原来的号
	 */
	public static final String BIND_MULTIPLE_PRODUCT_CAN_NOT_AUTHORIZE_NEW_USER = "230002";
	/**
	 * 重新授权的新号已经注册过了
	 */
	public static final String USER_IS_EXIST = "230003";
	/**
	 * 这个号绑定过了或者不是本地注册
	 */
	public static final String USER_NOT_REQUIRE_BIND = "230004";

	public TokenAuthorizeException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public TokenAuthorizeException(String errorCode) {
		super(errorCode);
	}
}
