package com.juzhai.passport.service;

import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.passport.model.Passport;

public interface IPassportRemoteService {

	/**
	 * 根据登录名搜索passport
	 * 
	 * @param loginName
	 * @return
	 */
	Passport getPassportByLoginName(String loginName);

	/**
	 * 根据Uid搜索Passport
	 * 
	 * @param uid
	 * @return
	 */
	Passport getPassportByUid(long uid);

	/**
	 * 登录之后所处理的事件
	 * 
	 * @param uid
	 */
	void loginProcess(Passport passport, long tpId, String remoteIp,
			RunType runType);
}
