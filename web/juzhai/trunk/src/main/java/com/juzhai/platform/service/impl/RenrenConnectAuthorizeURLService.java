package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.IAuthorizeURLService;
import com.renren.api.client.RenrenApiClient;

@Service
public class RenrenConnectAuthorizeURLService implements IAuthorizeURLService {

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, String turnTo) {
		String url = "";
		try {
			url = RenrenApiClient.getAuthorizeURLforCode(tp.getAppId(),
					tp.getAppUrl());
		} catch (UnsupportedEncodingException e) {
		}
		return url;
	}

}
