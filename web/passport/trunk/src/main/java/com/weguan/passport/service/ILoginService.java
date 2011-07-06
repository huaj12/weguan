package com.weguan.passport.service;

import com.weguan.passport.exception.LoginException;
import com.weguan.passport.model.Passport;

public interface ILoginService {

	/**
	 * 登录校验
	 * 
	 * @param loginName
	 * @param password
	 * @return 登录的passport对象
	 * @throws LoginException
	 */
	public Passport authenticate(String loginName, String password)
			throws LoginException;
}
