package com.juzhai.cms.service;

import java.util.List;

import com.juzhai.passport.model.LoginLog;

public interface IUserActionService {
	/**
	 * 查询
	 * 
	 * @param uid
	 * @return
	 */
	List<LoginLog> listUserLoginInfo(long uid, int firstResult, int maxResults);

	/**
	 * 统计某用户登陆总次数
	 * 
	 * @param uid
	 * @return
	 */
	int countUserLoginInfo(long uid);
}
