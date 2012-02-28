package com.juzhai.platform.service.impl;

import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IAdminService;

@Service
public class QqConnectAdminService implements IAdminService {

	@Override
	public boolean isAllocation(AuthInfo authInfo) {
		// tx没有查询配置的接口
		return true;
	}

}
