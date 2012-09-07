package com.juzhai.passport.service;

import com.juzhai.notice.bean.NoticeType;
import com.juzhai.passport.controller.form.EmailForm;
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

	/**
	 * 设置订阅
	 * 
	 * @param emailForm
	 * @throws ProfileInputException
	 */
	void setupSub(long uid, EmailForm emailForm) throws ProfileInputException;

	/**
	 * 是否订阅通知
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	boolean isSubNotice(long uid, NoticeType noticeType);
}
