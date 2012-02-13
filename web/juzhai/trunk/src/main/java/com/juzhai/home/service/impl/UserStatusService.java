package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.home.service.IUserStatusService;
import com.juzhai.notice.NoticeConfig;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class UserStatusService implements IUserStatusService {

	@Value("${user.weibo.expire.time}")
	private int userWeiboExpireTime;
	@Value("${user.status.size}")
	private int userStatusSize;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private ISynchronizeService synchronizeService;

	@Override
	public List<UserStatus> listUserStatus(long uid, long tpId, long fuid) {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		try {
			userStatusList = memcachedClient.get(MemcachedKeyGenerator
					.genUserWeiboKey(fuid));
		} catch (Exception e) {
		}
		if (CollectionUtils.isNotEmpty(userStatusList)) {
			return userStatusList;
		}
		AuthInfo authInfo = null;
		TpUser fUser = tpUserService.getTpUserByUid(fuid);
		if (fUser == null) {
			return null;
		}
		TpUser user = tpUserService.getTpUserByUid(uid);
		if (user == null || !user.getTpName().equals(fUser.getTpName())) {
			// 来访者和被访者不是同一个tpid
			// 获取小秘书的authinfo
			long tagerUid = NoticeConfig
					.getValue(ThirdpartyNameEnum.getThirdpartyNameEnum(fUser
							.getTpName()), "uid");
			long tagerTpId = NoticeConfig
					.getValue(ThirdpartyNameEnum.getThirdpartyNameEnum(fUser
							.getTpName()), "tpId");
			authInfo = tpUserAuthService.getAuthInfo(tagerUid, tagerTpId);
		} else {
			authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		}
		userStatusList = synchronizeService.listStatus(authInfo, fuid,
				userStatusSize);
		if (CollectionUtils.isNotEmpty(userStatusList)) {
			try {
				memcachedClient.set(
						MemcachedKeyGenerator.genUserWeiboKey(fuid),
						userWeiboExpireTime, userStatusList);
			} catch (Exception e) {
			}
		}
		return userStatusList;
	}

}
