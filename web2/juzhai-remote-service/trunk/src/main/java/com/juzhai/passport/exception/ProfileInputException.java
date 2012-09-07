package com.juzhai.passport.exception;

import com.juzhai.core.exception.JuzhaiException;

public class ProfileInputException extends JuzhaiException {

	private static final long serialVersionUID = 4890872155656488515L;

	public static final String PROFILE_ERROR = "20000";
	/**
	 * 邮箱非法
	 */
	public static final String PROFILE_EMAIL_INVALID = "20001";
	/**
	 * 性别非法
	 */
	public static final String PROFILE_GEBDER_INVALID = "20002";
	/**
	 * 性别已经修改过了
	 */
	public static final String PROFILE_GEBDER_REPEAT_UPDATE = "20003";

	public static final String PROFILE_UID_NOT_EXIST = "20004";

	public static final String PROFILE_NICKNAME_IS_NULL = "20005";

	public static final String PROFILE_NICKNAME_IS_TOO_LONG = "20006";

	public static final String PROFILE_NICKNAME_REPEAT_UPDATE = "20007";

	public static final String PROFILE_PROVINCE_IS_NULL = "20008";

	public static final String PROFILE_CITY_IS_NULL = "20009";

	public static final String PROFILE_TOWN_IS_NULL = "20023";

	public static final String PROFILE_BIRTH_YEAR_IS_NULL = "20010";

	public static final String PROFILE_BIRTH_MONTH_IS_NULL = "20011";

	public static final String PROFILE_BIRTH_DAY_IS_NULL = "20012";

	public static final String PROFILE_PROFESSION_ID_IS_NULL = "20013";

	public static final String PROFILE_PROFESSION_IS_NULL = "20014";

	public static final String PROFILE_PROFESSION_IS_TOO_LONG = "20015";

	public static final String PROFILE_FEATURE_IS_NULL = "20016";

	public static final String PROFILE_FEATURE_IS_TOO_LONG = "20017";

	public static final String PROFILE_NICKNAME_IS_EXIST = "20018";
	// 个人主页长度太长
	public static final String PROFILE_BLOG_IS_TOO_LONG = "20019";
	// 家乡长度太长
	public static final String PROFILE_HOME_IS_TOO_LONG = "20020";

	/**
	 * 昵称使用了屏蔽词
	 */
	public static final String PROFILE_NICKNAME_FORBID = "20021";

	/**
	 * 个人简介使用了屏蔽词
	 */
	public static final String PROFILE_FEATURE_FORBID = "20022";
	/**
	 * 个人主页地址有误
	 */
	public static final String PROFILE_BLOG_IS_ERROR = "20024";

	public ProfileInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public ProfileInputException(String errorCode) {
		super(errorCode);
	}
}
