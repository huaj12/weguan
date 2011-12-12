package com.juzhai.platform.service.impl;

import org.springframework.stereotype.Service;

import kx2_4j.KxSDK;

import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.IAuthorizeURLService;

@Service
public class Kaixin001ConnectAuthorizeURLService implements
		IAuthorizeURLService {

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp) {
		if (null == tp){
			return "";
		}
		KxSDK kxsdk = new KxSDK();
		kxsdk.CONSUMER_KEY = tp.getAppKey();
		kxsdk.CONSUMER_SECRET = tp.getAppSecret();
		kxsdk.Redirect_uri = tp.getAppUrl();
		String url = kxsdk.getAuthorizeURLforCode("", "", "");
		return url;
	}

}
