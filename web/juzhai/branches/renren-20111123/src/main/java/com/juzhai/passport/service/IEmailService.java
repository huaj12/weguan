package com.juzhai.passport.service;

import com.juzhai.passport.exception.ProfileInputException;

public interface IEmailService {

	/**
	 * 订阅邮箱
	 * 
	 * @param uid
	 * @param email
	 * @return
	 * @throws ProfileInputException
	 */
	boolean subEmail(long uid, String email) throws ProfileInputException;

	/**
	 * 退订邮箱
	 * 
	 * @param uid
	 * @return
	 */
	boolean unsubEmail(long uid);

	/**
	 * 统计打开邮件
	 * 
	 * @return
	 */
	long statOpenEmail();

	/**
	 * 获取打开邮件
	 * 
	 * @return
	 */
	long getOpenEmailStat();
}
