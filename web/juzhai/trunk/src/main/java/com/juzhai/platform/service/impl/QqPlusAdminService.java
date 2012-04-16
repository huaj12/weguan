package com.juzhai.platform.service.impl;

import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IAdminService;

@Service
public class QqPlusAdminService implements IAdminService {

	@Override
	public boolean isAllocation(AuthInfo authInfo) {
		return true;
	}

}
