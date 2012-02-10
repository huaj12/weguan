package com.juzhai.platform.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weibo4j.Account;
import weibo4j.model.RateLimitStatus;
import weibo4j.model.WeiboException;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.IAdminService;

@Service
public class WeiboConnectAdminService implements IAdminService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserAuthService tpUserAuthService;

	@Override
	public boolean isAllocation(long uid, long tpId) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		Account account = new Account(authInfo.getToken());
		try {
			RateLimitStatus rateLimitStatus = account
					.getAccountRateLimitStatus();
			long ipHits = rateLimitStatus.getRemainingIpHits();
			long userHits = rateLimitStatus.getRemainingUserHits();
			if (ipHits > 0 || userHits > 0) {
				return true;
			}
		} catch (WeiboException e) {
			log.error("isAllocation is error." + e.getMessage());
		}
		return false;
	}

}
