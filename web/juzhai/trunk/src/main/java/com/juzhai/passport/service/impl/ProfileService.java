/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IProfileImageService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;

@Service
public class ProfileService implements IProfileService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, byte[]> byteArrayRedisTemplate;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private IProfileImageService profileImageService;
	@Value("${profile.cache.expire.time}")
	private int profileCacheExpireTime = 20000;
	@Value("${nickname.length.max}")
	private int nickNameLengthMax;
	@Value("${profession.length.max}")
	private int professionLengthMax;
	@Value("${feature.length.max}")
	private int featureLengthMax;
	@Value("${blog.length.max}")
	private int blogLengthMax;
	@Value("${home.length.max}")
	private int homeLengthMax;

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
				TpUser tpUser = tpUserService.getTpUserByUid(uid);
				profileCache = cacheProfile(
						profile,
						null == tpUser ? StringUtils.EMPTY : tpUser
								.getTpIdentity());
			}
		}
		return profileCache;
	}

	@Override
	public ProfileCache cacheProfile(Profile profile, String tpIdentity) {
		Assert.notNull(profile, "The profile must not be null.");
		Assert.isTrue(profile.getUid() != null && profile.getUid() > 0,
				"Profile uid invalid.");
		ProfileCache profileCache = new ProfileCache();
		BeanUtils.copyProperties(profile, profileCache);
		profileCache.setTpIdentity(tpIdentity);
		cacheProfileCache(profile.getUid(), profileCache);
		return profileCache;
	}

	@Override
	public void cacheProfileCache(long uid, ProfileCache profileCache) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genProfileCacheKey(uid),
					profileCacheExpireTime, profileCache);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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

	@Override
	public byte[] getUserSecretKey(long uid) {
		String redisKey = RedisKeyGenerator.genUserSecretKey(uid);
		byte[] scretKey = byteArrayRedisTemplate.opsForValue().get(redisKey);
		if (ArrayUtils.isEmpty(scretKey)) {
			try {
				scretKey = DESUtils.generateKey();
			} catch (NoSuchAlgorithmException e) {
				log.error("generate scretKey error.[uid=" + uid + "]", e);
				return null;
			}
			byteArrayRedisTemplate.opsForValue().set(redisKey, scretKey);
		}
		return scretKey;
	}

	@Override
	public void updateLastUpdateTime(long uid) {
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setLastUpdateTime(new Date());
		profileMapper.updateByPrimaryKeySelective(profile);
	}

	@Override
	public List<Profile> listProfileByIdsOrderByLastUpdateTime(List<Long> uids,
			int firstResult, int maxResults) {
		if (CollectionUtils.isEmpty(uids)) {
			return Collections.emptyList();
		}
		ProfileExample example = new ProfileExample();
		example.createCriteria().andUidIn(uids);
		example.setOrderByClause("last_modify_time desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return profileMapper.selectByExample(example);
	}

	@Override
	public Profile getProfile(long uid) {
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		return profile;
	}

	@Override
	public void setGender(long uid, Integer gender)
			throws ProfileInputException {
		if (gender == null || (gender != 0 && gender != 1)) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_GEBDER_INVALID);
		}
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		if (null == profile) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_UID_NOT_EXIST);
		}
		if (!profile.getHasModifyGender()) {
			profile.setGender(gender);
			profile.setLastModifyTime(new Date());
			profile.setHasModifyGender(true);
			try {
				profileMapper.updateByPrimaryKey(profile);
			} catch (Exception e) {
				throw new ProfileInputException(
						ProfileInputException.PROFILE_ERROR);
			}
			clearProfileCache(uid);
		} else {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_GEBDER_REPEAT_UPDATE);
		}

	}

	@Override
	public void setNickName(long uid, String nickName)
			throws ProfileInputException {
		if (StringUtils.isEmpty(nickName)) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_NULL);
		}
		if (StringUtil.chineseLength(nickName) > nickNameLengthMax) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_TOO_LONG);
		}
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		if (null == profile) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_UID_NOT_EXIST);
		}
		if (profile.getHasModifyNickname()) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_REPEAT_UPDATE);
		}
		if (isExistNickname(nickName, uid)) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_EXIST);
		}
		profile.setNickname(nickName);
		profile.setLastModifyTime(new Date());
		profile.setHasModifyNickname(true);
		try {
			profileMapper.updateByPrimaryKey(profile);
		} catch (Exception e) {
			throw new ProfileInputException(ProfileInputException.PROFILE_ERROR);
		}
		clearProfileCache(uid);

	}

	@Override
	public void updateProfile(Profile profile) throws ProfileInputException {
		if (null == profile || profile.getUid() == 0) {
			return;
		}
		long uid = profile.getUid();
		if (profile.getProvince() == null) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_PROVINCE_IS_NULL);
		}
		if (profile.getCity() == null) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_CITY_IS_NULL);
		}
		if (profile.getBirthYear() == null) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BIRTH_YEAR_IS_NULL);
		}
		if (profile.getBirthMonth() == null) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BIRTH_MONTH_IS_NULL);
		}
		if (profile.getBirthDay() == null) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BIRTH_DAY_IS_NULL);
		}
		if (profile.getProfessionId() == null) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_PROFESSION_ID_IS_NULL);
		}
		if (profile.getProfessionId() == 0
				&& StringUtils.isEmpty(profile.getProfession())) {
			// 选择其他职业描述不能为空
			throw new ProfileInputException(
					ProfileInputException.PROFILE_PROFESSION_IS_NULL);
		}
		if (profile.getProfessionId() == 0
				&& StringUtil.chineseLength(profile.getProfession()) > professionLengthMax) {
			// 职业描述不能大于10个字
			throw new ProfileInputException(
					ProfileInputException.PROFILE_PROFESSION_IS_TOO_LONG);
		}
		if (null != profile.getFeature()) {
			if (StringUtils.isEmpty(profile.getFeature())) {
				// 性格描述不能为空
				throw new ProfileInputException(
						ProfileInputException.PROFILE_FEATURE_IS_NULL);
			}
			if (StringUtil.chineseLength(profile.getFeature()) > featureLengthMax) {
				throw new ProfileInputException(
						ProfileInputException.PROFILE_FEATURE_IS_TOO_LONG);
			}
		}
		// 验证个人主页长度
		if (StringUtil.chineseLength(profile.getBlog()) > blogLengthMax) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BLOG_IS_TOO_LONG);
		}

		// 验证家乡长度
		if (StringUtil.chineseLength(profile.getHome()) > homeLengthMax) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_HOME_IS_TOO_LONG);
		}

		if (profile.getProfessionId() > 0) {
			Profession p = InitData.PROFESSION_MAP.get(profile
					.getProfessionId());
			if (null != p) {
				profile.setProfession(p.getName());
			}
		}
		profile.setConstellationId(InitData.getConstellation(
				profile.getBirthMonth(), profile.getBirthDay()).getId());
		profile.setLastModifyTime(new Date());
		try {
			profileMapper.updateByPrimaryKeySelective(profile);
		} catch (Exception e) {
			throw new ProfileInputException(ProfileInputException.PROFILE_ERROR);
		}
		clearProfileCache(uid);
		cacheUserCity(uid);
	}

	@Override
	public boolean isExistNickname(String nickname, long uid) {
		if (StringUtils.isEmpty(nickname) || uid == 0) {
			return false;
		}
		ProfileExample example = new ProfileExample();
		example.createCriteria().andNicknameEqualTo(nickname)
				.andUidNotEqualTo(uid);
		return profileMapper.countByExample(example) > 0 ? true : false;
	}

	@Override
	public void clearProfileCache(long uid) {
		if (uid == 0) {
			return;
		}
		String key = MemcachedKeyGenerator.genProfileCacheKey(uid);
		try {
			memcachedClient.delete(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void updateLogo(long uid, String filePath, int scaledW, int scaledH,
			int x, int y, int w, int h) throws UploadImageException {
		String logo = profileImageService.cutAndReduceLogo(uid, filePath,
				scaledW, scaledH, x, y, w, h);
		if (StringUtils.isEmpty(logo)) {
			return;
		}
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setNewLogoPic(logo);
		profile.setLogoVerifyState(LogoVerifyState.VERIFYING.getType());
		// profile.setLogoPic(logo);
		profile.setLastModifyTime(new Date());
		profileMapper.updateByPrimaryKeySelective(profile);
		clearProfileCache(uid);
	}

	@Override
	public List<Profile> listProfileOrderByLoginWebTime(List<Long> uids,
			Integer gender, Long city, List<Long> exceptUids, int firstResult,
			int maxResults) {
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria();
		if (CollectionUtils.isNotEmpty(uids)) {
			c.andUidIn(uids);
		}
		if (null != gender) {
			c.andGenderEqualTo(gender);
		}
		if (null != city && city > 0) {
			c.andCityEqualTo(city);
		}
		if (CollectionUtils.isNotEmpty(exceptUids)) {
			c.andUidNotIn(exceptUids);
		}
		example.setOrderByClause("last_web_login_time desc, uid desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return profileMapper.selectByExample(example);
	}

	@Override
	public int countProfile(List<Long> uids, Integer gender, Long city,
			List<Long> exceptUids) {
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria();
		if (CollectionUtils.isNotEmpty(uids)) {
			c.andUidIn(uids);
		}
		if (null != gender) {
			c.andGenderEqualTo(gender);
		}
		if (null != city && city > 0) {
			c.andCityEqualTo(city);
		}
		if (CollectionUtils.isNotEmpty(exceptUids)) {
			c.andUidNotIn(exceptUids);
		}
		return profileMapper.countByExample(example);
	}

	@Override
	public int countQueryProfile(long excludeUid, Integer gender, long city,
			int minYear, int maxYear) {
		ProfileExample example = getProfileExample(excludeUid, gender, city,
				minYear, maxYear);
		return profileMapper.countByExample(example);
	}

	@Override
	public List<Profile> queryProfile(long excludeUid, Integer gender,
			long city, int minYear, int maxYear, int firstResult, int maxResults) {
		ProfileExample example = getProfileExample(excludeUid, gender, city,
				minYear, maxYear);
		example.setOrderByClause("last_web_login_time desc, uid desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return profileMapper.selectByExample(example);
	}

	private ProfileExample getProfileExample(long excludeUid, Integer gender,
			long city, int minYear, int maxYear) {
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria()
				.andLogoPicIsNotNull().andLogoPicNotEqualTo(StringUtils.EMPTY);
		if (excludeUid > 0) {
			c.andUidNotEqualTo(excludeUid);
		}
		if (city > 0) {
			c.andCityEqualTo(city);
		}
		if (gender != null) {
			c.andGenderEqualTo(gender);
		}
		if (maxYear == 0 && minYear > 0) {
			c.andBirthYearGreaterThanOrEqualTo(minYear);
		} else if (maxYear > 0 && minYear == 0) {
			c.andBirthYearLessThanOrEqualTo(maxYear);
		} else if (maxYear > 0 && minYear > 0) {
			c.andBirthYearBetween(minYear, maxYear);
		}
		return example;
	}

	@Override
	public List<Profile> listProfileByCityIdOrderCreateTime(Long cityId,
			int firstResult, int maxResults) {
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria()
				.andLogoPicIsNotNull().andLogoPicNotEqualTo(StringUtils.EMPTY);
		if (null != cityId && cityId > 0) {
			c.andCityEqualTo(cityId);
		}
		example.setOrderByClause("create_time desc, uid desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return profileMapper.selectByExample(example);
	}

	@Override
	public int getProfileCompletion(long uid) {
		int result = 0;
		ProfileCache profileCache = getProfileCacheByUid(uid);
		if (null != profileCache) {
			if (StringUtils.isNotEmpty(profileCache.getNickname())) {
				result += 5;
			}
			if (null != profileCache.getGender()) {
				result += 5;
			}
			if (null != profileCache.getCity() && profileCache.getCity() > 0) {
				result += 10;
			}
			if (null != profileCache.getBirthYear()
					&& profileCache.getBirthYear() > 0
					&& null != profileCache.getBirthMonth()
					&& profileCache.getBirthMonth() > 0
					&& null != profileCache.getBirthDay()
					&& profileCache.getBirthDay() > 0) {
				result += 10;
			}
			if (StringUtils.isNotEmpty(profileCache.getProfession())) {
				result += 10;
			}
			if (StringUtils.isNotEmpty(profileCache.getFeature())) {
				result += 10;
			}
			if (StringUtils.isNotEmpty(profileCache.getBlog())) {
				result += 5;
			}
			if (null != profileCache.getHeight()
					&& profileCache.getHeight() > 0) {
				result += 10;
			}
			if (StringUtils.isNotEmpty(profileCache.getBloodType())) {
				result += 5;
			}
			if (StringUtils.isNotEmpty(profileCache.getEducation())) {
				result += 10;
			}
			if ((null != profileCache.getMinMonthlyIncome() && profileCache
					.getMinMonthlyIncome() > 0)
					|| (null != profileCache.getMaxMonthlyIncome() && profileCache
							.getMaxMonthlyIncome() > 0)) {
				result += 5;
			}
			if (StringUtils.isNotEmpty(profileCache.getHouse())) {
				result += 5;
			}
			if (StringUtils.isNotEmpty(profileCache.getCar())) {
				result += 5;
			}
			if (StringUtils.isNotEmpty(profileCache.getHome())) {
				result += 5;
			}
		}
		return result;
	}

}
