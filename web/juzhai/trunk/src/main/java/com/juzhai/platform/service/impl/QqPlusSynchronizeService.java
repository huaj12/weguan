package com.juzhai.platform.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class QqPlusSynchronizeService implements ISynchronizeService {

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		return Collections.emptyList();
	}

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {

	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {

	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {

	}

}
