package com.juzhai.platform.service;

import java.util.List;

import com.juzhai.core.bean.DeviceName;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;

public interface IUserService extends IUserRemoteService {

	/**
	 * 获取用户名字
	 * 
	 * @param authInfo
	 * @param uids
	 * @return
	 */
	List<String> getUserNames(AuthInfo authInfo, List<String> fuids);

	/**
	 * 注册成功后的操作
	 * 
	 * @param authInfo
	 * @param deviceName
	 */
	void registerSucesssAfter(Thirdparty tp, AuthInfo authInfo,
			DeviceName deviceName);

}
