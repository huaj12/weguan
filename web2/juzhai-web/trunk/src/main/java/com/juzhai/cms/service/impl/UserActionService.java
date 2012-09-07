package com.juzhai.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.IUserActionService;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.LoginLogMapper;
import com.juzhai.passport.model.LoginLog;
import com.juzhai.passport.model.LoginLogExample;

@Service
public class UserActionService implements IUserActionService {
	@Autowired
	private LoginLogMapper loginLogMapper;

	@Override
	public List<LoginLog> listUserLoginInfo(long uid, int firstResult,
			int maxResults) {
		LoginLogExample example = getLoginLogExample(uid);
		example.setLimit(new Limit(firstResult, maxResults));
		return loginLogMapper.selectByExample(example);
	}

	@Override
	public int countUserLoginInfo(long uid) {
		LoginLogExample example = getLoginLogExample(uid);
		return loginLogMapper.countByExample(example);
	}

	private LoginLogExample getLoginLogExample(long uid) {
		LoginLogExample example = new LoginLogExample();
		example.createCriteria().andUidEqualTo(uid);
		return example;
	}

}
