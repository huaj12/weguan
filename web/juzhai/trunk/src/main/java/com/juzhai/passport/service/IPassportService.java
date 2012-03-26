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
	 * 总数
	 * 
	 * @return
	 */
	int totalCount();
}
