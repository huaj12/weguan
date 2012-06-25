package com.juzhai.cms.exception;

import com.juzhai.core.exception.JuzhaiException;

public class RobotInputException extends JuzhaiException {

	private static final long serialVersionUID = 22157769638680383L;

	/**
	 * 导入文件不能为空
	 */
	public static final String ROBOT_FILE_IS_NULL = "210001";

	/**
	 * 读取文件错误
	 */
	public static final String ROBOT_READ_FILE_IS_INVALID = "210002";

	/**
	 * 机器人不存在请先添加机器人
	 */
	public static final String ROBOT_NOT_EXIST = "210003";

	/**
	 * 机器人留言内容不能为空
	 */
	public static final String ROBOT_COMMON_TEXT_IS_NULL = "210004";

	public static final String ROBOT_CITY_IS_NULL = "210005";

	public RobotInputException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public RobotInputException(String errorCode) {
		super(errorCode);
	}
}
