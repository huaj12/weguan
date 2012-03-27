package com.juzhai.passport.service;

import com.juzhai.passport.model.Passport;

public interface IPassportService {

	/**
	 * 根据Uid搜索Passport
	 * 
	 * @param uid
	 * @return
	 */
	Passport getPassportByUid(long uid);

	/**
	 * 锁定用户
	 * 
	 * @param uid
	 * @param time锁定时间time
	 *            ==0则解锁
	 */
	void lockUser(long uid, long time);

	/**
	 * 总数
	 * 
	 * @return
	 */
	int totalCount();
}
