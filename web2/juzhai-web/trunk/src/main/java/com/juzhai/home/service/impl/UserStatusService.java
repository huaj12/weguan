package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.service.IUserStatusService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class UserStatusService implements IUserStatusService {
	private final Log log = LogFactory.getLog(getClass());
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
	@Autowired
	private RedisTemplate<String, List<UserStatus>> redisTemplate;

	@Deprecated
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
			authInfo = tpUserAuthService.getSecretary(fUser.getTpName());
		} else {
			authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		}

		if (authInfo == null) {
			return null;
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

	@Override
	public void updateUserStatus(long uid, long tpId) {
		if (isExpired(uid)) {
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
			List<UserStatus> userStatusList = synchronizeService.listStatus(
					authInfo, uid, userStatusSize);
			if (CollectionUtils.isNotEmpty(userStatusList)) {
				redisTemplate.opsForValue()
						.set(RedisKeyGenerator.genUserStatusKey(uid),
								userStatusList);
				touchCache(uid);
			}
		}

	}

	@Override
	public List<UserStatus> listUserStatus(long fuid) {
		List<UserStatus> userStatusList = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserStatusKey(fuid));
		return userStatusList;
	}

	private boolean isExpired(long uid) {
		Boolean cached = null;
		try {
			cached = memcachedClient.get(MemcachedKeyGenerator
					.genUserWeiboKey(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return cached == null || !cached;
	}

	private void touchCache(long uid) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genUserWeiboKey(uid),
					userWeiboExpireTime, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
