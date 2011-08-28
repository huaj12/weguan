/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;

@Service
public class ProfileService implements IProfileService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${profile.cache.expire.time}")
	private int profileCacheExpireTime = 20000;

	@Override
	public void cacheUserCity(long uid) {
		// 初始化所在地等需要匹配的信息（redis）
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		cacheUserCity(profile);
	}

	@Override
	public void cacheUserCity(Profile profile) {
		// 初始化所在地等需要匹配的信息（redis）
		if (null != profile && profile.getUid() != null && profile.getUid() > 0
				&& profile.getCity() != null && profile.getCity() > 0) {
			redisTemplate.opsForValue().set(
					RedisKeyGenerator.genUserCityKey(profile.getUid()),
					profile.getCity());
		}

	}

	@Override
	public long getUserCityFromCache(long uid) {
		Long cityId = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserCityKey(uid));
		return cityId == null ? null : cityId;
	}

	@Override
	public List<Profile> getProfilesByCityId(long cityId) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andCityEqualTo(cityId);
		return profileMapper.selectByExample(example);
	}

	@Override
	public ProfileCache getProfileCacheByUid(long uid) {
		ProfileCache profileCache = null;
		try {
			profileCache = memcachedClient.getAndTouch(
					MemcachedKeyGenerator.genProfileCacheKey(uid),
					profileCacheExpireTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == profileCache) {
			Profile profile = profileMapper.selectByPrimaryKey(uid);
			if (profile != null) {
				profileCache = cacheProfile(profile);
			}
		}
		return profileCache;
	}

	@Override
	public ProfileCache cacheProfile(Profile profile) {
		Assert.notNull(profile, "The profile must not be null.");
		Assert.isTrue(profile.getUid() != null && profile.getUid() > 0,
				"Profile uid invalid.");
		ProfileCache profileCache = new ProfileCache();
		BeanUtils.copyProperties(profile, profileCache);
		try {
			memcachedClient.set(
					MemcachedKeyGenerator.genProfileCacheKey(profile.getUid()),
					profileCacheExpireTime, profileCache);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return profileCache;
	}
}
