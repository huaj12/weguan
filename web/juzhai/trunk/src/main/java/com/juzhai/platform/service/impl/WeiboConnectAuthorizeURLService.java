package com.juzhai.platform.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.IAuthorizeURLService;

@Service
public class WeiboConnectAuthorizeURLService implements IAuthorizeURLService {

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, String turnTo) {
		String url = "";
		String redirectURL = tp.getAppUrl();
		if (StringUtils.isNotEmpty(turnTo)) {
			redirectURL = redirectURL + "?turnTo=" + turnTo;
		}
		try {
			Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
					redirectURL);
			url = oauth.authorize("code");
		} catch (WeiboException e) {
		}
		return url;
	}

}
