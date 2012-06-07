/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IProfileImageService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.search.service.IProfileSearchService;
import com.juzhai.wordfilter.service.IWordFilterService;

@Service
public class ProfileService implements IProfileService {

	private final Log log = LogFactory.getLog(getClass());

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
	@Autowired
	private IWordFilterService wordFilterService;
	@Autowired
	private IProfileSearchService profileSearchService;
	@Autowired
	private IUserGuideService userGuideService;
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
	@Value("${profile.nickname.wordfilter.application}")
	private int profileNicknameWordfilterApplication;
	@Value("${profile.feature.wordfilter.application}")
	private int profileFeatureWordfilterApplication;

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
	public void updateLastUpdateTime(long uid, Date date) {
		Profile profile = new Profile();
		profile.setUid(uid);
		profile.setLastUpdateTime(date);
		profileMapper.updateByPrimaryKeySelective(profile);
	}

	@Override
	public void delLastUpdateTime(long uid) {
		Profile profile = getProfile(uid);
		profile.setLastUpdateTime(null);
		profileMapper.updateByPrimaryKey(profile);
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
			// 修改性别
			// TODO 想办法更新索引个别field
			if (isValidUser(uid)) {
				profileSearchService.updateIndex(uid);
			}
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
		if (isExistNickname(nickName, uid)) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_EXIST);
		}
		// 验证屏蔽字
		try {
			if (wordFilterService.wordFilter(
					profileNicknameWordfilterApplication, uid, null,
					nickName.getBytes("GBK")) < 0) {
				throw new ProfileInputException(
						ProfileInputException.PROFILE_NICKNAME_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
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
		profile.setNickname(nickName);
		profile.setLastModifyTime(new Date());
		profile.setHasModifyNickname(true);
		try {
			profileMapper.updateByPrimaryKey(profile);
		} catch (Exception e) {
			throw new ProfileInputException(ProfileInputException.PROFILE_ERROR);
		}
		clearProfileCache(uid);
		// 修改性别
		// TODO 想办法更新索引个别field
		if (isValidUser(uid)) {
			profileSearchService.updateIndex(uid);
		}
	}

	private boolean isTown(long cityId) {
		boolean flag = false;
		for (Entry<Long, Town> entry : com.juzhai.common.InitData.TOWN_MAP
				.entrySet()) {
			if (cityId == entry.getValue().getCityId()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public void updateProfile(Profile profile) throws ProfileInputException {
		// cacheUserCity(uid);
		// 引导也用的这个没法判断索引是否存在
		// 用户资料修改
		// 引导页面（后台头像已通过）
		update(profile);
		if (isValidUser(profile.getUid())) {
			profileSearchService.updateIndex(profile.getUid());
		}
	}

	@Override
	public boolean isExistNickname(String nickname, long uid) {
		if (StringUtils.isEmpty(nickname)) {
			return false;
		}
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria()
				.andNicknameEqualTo(nickname);
		if (uid > 0) {
			c.andUidNotEqualTo(uid);
		}
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
	public int countQueryProfile(long excludeUid, Integer gender, Long cityId,
			Long townId, int minYear, int maxYear) {
		ProfileExample example = getProfileExample(excludeUid, gender, cityId,
				townId, minYear, maxYear);
		return profileMapper.countByExample(example);
	}

	@Override
	public List<Profile> queryProfile(long excludeUid, Integer gender,
			Long cityId, Long townId, int minYear, int maxYear,
			int firstResult, int maxResults) {
		ProfileExample example = getProfileExample(excludeUid, gender, cityId,
				townId, minYear, maxYear);
		example.setOrderByClause("last_web_login_time desc, uid desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return profileMapper.selectByExample(example);
	}

	private ProfileExample getProfileExample(long excludeUid, Integer gender,
			Long cityId, Long townId, int minYear, int maxYear) {
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria()
				.andLogoPicIsNotNull().andLogoPicNotEqualTo(StringUtils.EMPTY)
				.andLastUpdateTimeIsNotNull();
		if (excludeUid > 0) {
			c.andUidNotEqualTo(excludeUid);
		}
		if (null != cityId && cityId > 0) {
			c.andCityEqualTo(cityId);
		}
		if (null != townId && townId > 0) {
			c.andTownEqualTo(townId);
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

	@Override
	public boolean isValidUser(long uid) {
		// 未通过引导
		if (!userGuideService.isCompleteGuide(uid)) {
			return false;
		}
		if (!isValidLogo(uid)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidLogo(long uid) {
		ProfileCache cache = getProfileCacheByUid(uid);
		if (StringUtils.isEmpty(cache.getLogoPic())) {
			return false;
		}
		return true;
	}

	@Override
	public void nextGuide(Profile profile) throws ProfileInputException {
		update(profile);
		if (isValidLogo(profile.getUid())) {
			profileSearchService.createIndex(profile.getUid());
		}
	}

	private void update(Profile profile) throws ProfileInputException {
		if (null == profile || profile.getUid() == 0) {
			return;
		}
		long uid = profile.getUid();
		if (profile.getProvince() == null || profile.getProvince() == 0) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_PROVINCE_IS_NULL);
		}
		if (profile.getCity() == null || profile.getCity() == 0) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_CITY_IS_NULL);
		}
		if (isTown(profile.getCity()) && profile.getTown() == -1) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_TOWN_IS_NULL);
		}
		if (profile.getBirthYear() == null || profile.getBirthYear() == 0) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BIRTH_YEAR_IS_NULL);
		}
		if (profile.getBirthMonth() == null || profile.getBirthMonth() == 0) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BIRTH_MONTH_IS_NULL);
		}
		if (profile.getBirthDay() == null || profile.getBirthDay() == 0) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BIRTH_DAY_IS_NULL);
		}
		if (profile.getProfessionId() == null
				|| profile.getProfessionId() == -1) {
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
			// 验证屏蔽字
			try {
				if (wordFilterService.wordFilter(
						profileFeatureWordfilterApplication, uid, null, profile
								.getFeature().getBytes("GBK")) < 0) {
					throw new ProfileInputException(
							ProfileInputException.PROFILE_FEATURE_FORBID);
				}
			} catch (IOException e) {
				log.error("Wordfilter service down.", e);
			}
		}
		// 验证个人主页长度
		if (StringUtil.chineseLength(profile.getBlog()) > blogLengthMax) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_BLOG_IS_TOO_LONG);
		}
		if (StringUtils.isNotEmpty(profile.getBlog())) {
			// 替换掉http://
			profile.setBlog(profile.getBlog().replace("http://", ""));
			// 验证个人主页格式
			Pattern pat = Pattern
					.compile("((\\w)+?\\.){1,}(\\w{1,3})((\\/[\\w\\.\\/\\?\\%\\&\\=\\#]*)*?)");
			Matcher matcher = pat.matcher(profile.getBlog());
			if (!matcher.matches()) {
				throw new ProfileInputException(
						ProfileInputException.PROFILE_BLOG_IS_ERROR);
			}
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
	}
}
