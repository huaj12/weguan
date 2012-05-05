package com.juzhai.platform.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.platform.service.IRelationshipService;

@Service
public class QqPlusRelationshipService implements IRelationshipService {

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		return Collections.emptyList();
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		return Collections.emptyList();
	}

	@Override
	public List<String> getInstallFollows(AuthInfo authInfo) {
		return Collections.emptyList();
	}

}
