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
	@Value("${update.user.online.expire.time}")
	private int updateUserOnlineExpireTime;

	@Override
	public void setLastUserOnlineTime(long uid) {
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setLastUserOnlineTime(new Date());
		profileMapper.updateByPrimaryKeySelective(profile);
		try {
			memcachedClient.set(
					MemcachedKeyGenerator.genIsUpdateUserOnlineKey(uid),
					updateUserOnlineExpireTime, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public boolean isUpdateUserOnlineTime(long uid) {
		try {
			Object obj = memcachedClient.get(MemcachedKeyGenerator
					.genIsUpdateUserOnlineKey(uid));
			if (obj == null) {
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}
}
