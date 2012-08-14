package com.juzhai.passport.service.impl;

import java.util.Date;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IUserOnlineService;

@Service
public class UserOnlineService implements IUserOnlineService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${user.last.online.expire.time}")
	private int userLastOnlineExpireTime;
	@Value("${user.online.interval.time}")
	private int userOnlineIntervalTime;

	@Override
	public void setLastUserOnlineTime(long uid) {
		Date cDate = new Date();
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setLastUserOnlineTime(cDate);
		profileMapper.updateByPrimaryKeySelective(profile);
		updateUserOnlineTimeCache(uid, cDate);
	}

	@Override
	public boolean isUpdateUserOnlineTime(long uid) {
		try {
			Long cDate = memcachedClient.get(MemcachedKeyGenerator
					.genUserOnlineTimeKey(uid));
			if (cDate == null) {
				return true;
			}
			long time = (System.currentTimeMillis() - cDate) / 1000;
			if (time > userOnlineIntervalTime) {
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public void updateUserOnlineTimeCache(long uid, Date cDate) {
		try {
			memcachedClient.set(
					MemcachedKeyGenerator.genUserOnlineTimeKey(uid),
					userLastOnlineExpireTime, cDate.getTime());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public Date getLastUserOnlineTime(long uid) {
		try {
			Long time = memcachedClient.get(MemcachedKeyGenerator
					.genUserOnlineTimeKey(uid));
			if (null == time || time == 0) {
				return null;
			}
			return new Date(time);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
