package com.juzhai.passport.service.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.passport.service.IUserSetupService;

//@Service
public class UserSetupService implements IUserSetupService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${tp.advise.expire.time}")
	private int tpAdviseExpireTime;

	@Override
	public boolean setupTpAdvise(long uid, boolean isAdvise) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genSetupTpAdviseKey(uid),
					tpAdviseExpireTime, isAdvise);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public boolean isTpAdvise(long uid) {
		try {
			Boolean isAdvise = memcachedClient.get(MemcachedKeyGenerator
					.genSetupTpAdviseKey(uid));
			return null == isAdvise ? true : isAdvise;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}
}
