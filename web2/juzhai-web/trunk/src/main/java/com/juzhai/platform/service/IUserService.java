package com.juzhai.platform.service;

import java.util.List;

import com.juzhai.passport.bean.AuthInfo;

public interface IUserService extends IUserRemoteService {

	/**
	 * 获取用户名字
	 * 
	 * @param authInfo
	 * @param uids
	 * @return
	 */
	List<String> getUserNames(AuthInfo authInfo, List<String> fuids);

}
