package com.juzhai.passport.service.impl;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IEmailService;
import com.juzhai.passport.service.IProfileService;

@Service
public class EmailService implements IEmailService {

	public static final String EMAIL_PATTERN_STRING = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$";
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile(EMAIL_PATTERN_STRING);
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

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
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(uid);
			boolean isFirst = false;
			if (null != profileCache) {
				if (StringUtils.isEmpty(profileCache.getEmail())) {
					isFirst = true;
				}
				profileCache.setEmail(email);
				profileCache.setSubEmail(true);
				profileService.cacheProfileCache(uid, profileCache);
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
	public boolean unsubEmail(long uid) {
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setSubEmail(false);
		profile.setLastModifyTime(new Date());
		if (profileMapper.updateByPrimaryKeySelective(profile) == 1) {
			// 更新cache
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(uid);
			if (null != profileCache) {
				profileCache.setSubEmail(false);
				profileService.cacheProfileCache(uid, profileCache);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long statOpenEmail() {
		Long value = redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genOpenEmailStatKey(), 1);
		return value == null ? 0 : value;
	}

	@Override
	public long getOpenEmailStat() {
		Long value = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genOpenEmailStatKey());
		return value == null ? 0 : value;
	}

}
