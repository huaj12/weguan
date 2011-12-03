/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;

public interface IRegisterService {

	/**
	 * 自动注册（第三方用户）
	 * 
	 * @param tp
	 * @param identity
	 * @param profile
	 * @return
	 */
	public long autoRegister(Thirdparty tp, String identity, AuthInfo authInfo,
			Profile profile);
}
