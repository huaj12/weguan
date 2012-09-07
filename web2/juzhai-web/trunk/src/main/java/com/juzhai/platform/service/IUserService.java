package com.juzhai.platform.service;

import java.util.List;
import java.util.Map;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.exception.TokenAuthorizeException;

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
