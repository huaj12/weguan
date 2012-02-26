package com.juzhai.platform.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import weibo4j.Account;
import weibo4j.model.RateLimitStatus;
import weibo4j.model.WeiboException;

import com.google.gdata.client.douban.DoubanService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IAdminService;

@Service
public class DoubanConnectAdminService implements IAdminService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean isAllocation(AuthInfo authInfo) {
		//豆瓣没有查询配置的接口
		return true;
	}

}
