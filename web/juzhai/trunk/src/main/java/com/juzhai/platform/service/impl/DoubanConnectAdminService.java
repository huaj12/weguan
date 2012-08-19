package com.juzhai.platform.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.IAdminService;

@Service
public class DoubanConnectAdminService implements IAdminService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean isAllocation(AuthInfo authInfo) {
		// 豆瓣没有查询配置的接口
		return true;
	}

	@Override
	public boolean isTokenExpired(AuthInfo authInfo) {
		try {
			Thirdparty tp = InitData.getTpByTpNameAndJoinType(
					authInfo.getThirdpartyName(),
					JoinTypeEnum.getJoinTypeEnum(authInfo.getJoinType()));
			DoubanService doubanService = DoubanService.getDoubanService(
					authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getAppKey(), authInfo.getAppSecret(),
					tp.getAppId());
			doubanService.getUserMiniblogs(authInfo.getTpIdentity(), 1, 10);
		} catch (AuthenticationException e) {
			return true;
		} catch (IOException e) {
			log.error(" douban isTokenExpired is error", e);
		} catch (ServiceException e) {
			log.error(" douban isTokenExpired is error", e);
		}
		return false;
	}

}
