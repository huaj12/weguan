/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;

@Service
public class ProfileService implements IProfileService {

	private final Log log = LogFactory.getLog(getClass());

	public static final String EMAIL_PATTERN_STRING = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$";
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile(EMAIL_PATTERN_STRING);

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private IAccountService accountService;
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
		return cityId == null ? 0L : cityId;
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
			profileCache = memcachedClient.get(MemcachedKeyGenerator
					.genProfileCacheKey(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// TODO will change to getAndTouch by memcached 1.6.x
		if (null != profileCache) {
			cacheProfileCache(uid, profileCache);
		} else {
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
		cacheProfileCache(profile.getUid(), profileCache);
		return profileCache;
	}

	private void cacheProfileCache(long uid, ProfileCache profileCache) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genProfileCacheKey(uid),
					profileCacheExpireTime, profileCache);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean subEmail(long uid, String email)
			throws ProfileInputException {
		email = StringUtils.trim(email);
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_EMAIL_INVALID);
		}
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setEmail(email);
		profile.setSubEmail(true);
		profile.setLastModifyTime(new Date());

		if (profileMapper.updateByPrimaryKeySelective(profile) == 1) {
			// 更新cache
			ProfileCache profileCache = getProfileCacheByUid(uid);
			boolean isFirst = false;
			if (null != profileCache) {
				if (StringUtils.isNotEmpty(profileCache.getEmail())) {
					isFirst = true;
				}
				profileCache.setEmail(email);
				profileCache.setSubEmail(true);
				cacheProfileCache(uid, profileCache);
			}
			if (isFirst) {
				accountService.profitPoint(uid, ProfitAction.SUB_EMAIL);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int isMaybeSameCity(long uid1, long uid2) {
		long srcUserCity = getUserCityFromCache(uid1);
		long destUserCity = getUserCityFromCache(uid2);
		if (0 == srcUserCity || 0 == destUserCity) {
			return 1;
		} else if (srcUserCity == destUserCity) {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public List<Profile> getSubEmailProfiles(int firstResult, int maxResults) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andSubEmailEqualTo(true).andEmailIsNotNull()
				.andEmailNotEqualTo(StringUtils.EMPTY);
		example.setOrderByClause("uid asc");
		example.setLimit(new Limit(firstResult, maxResults));
		return profileMapper.selectByExample(example);
	}
}
