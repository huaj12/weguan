package com.juzhai.cms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.IVerifyLogoService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.stats.counter.service.ICounter;

@Service
public class VerifyLogoService implements IVerifyLogoService {

	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private ICounter auditLogoCounter;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private PostMapper postMapper;

	@Override
	public List<Profile> listVerifyLogoProfile(LogoVerifyState logoVerifyState,
			int firstResult, int maxResult) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andNewLogoPicNotEqualTo(StringUtils.EMPTY)
				.andNewLogoPicIsNotNull()
				.andLogoVerifyStateEqualTo(logoVerifyState.getType());
		example.setLimit(new Limit(firstResult, maxResult));
		example.setOrderByClause("last_modify_time desc");
		return profileMapper.selectByExample(example);
	}

	@Override
	public int countVerifyLogoProfile(LogoVerifyState logoVerifyState) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andNewLogoPicNotEqualTo(StringUtils.EMPTY)
				.andNewLogoPicIsNotNull()
				.andLogoVerifyStateEqualTo(logoVerifyState.getType());
		return profileMapper.countByExample(example);
	}

	@Override
	public void passLogo(long uid) {
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		if (null != profile) {
			Profile updateProfile = new Profile();
			updateProfile.setUid(uid);
			updateProfile.setLogoPic(profile.getNewLogoPic());
			updateProfile
					.setLogoVerifyState(LogoVerifyState.VERIFIED.getType());
			if (profileMapper.updateByPrimaryKeySelective(updateProfile) > 0) {
				ProfileCache profileCache = profileService
						.getProfileCacheByUid(uid);
				if (null != profileCache) {
					profileService.clearProfileCache(uid);
					dialogService.sendOfficialSMS(uid,
							DialogContentTemplate.PASS_LOGO,
							profileCache.getNickname());
					auditLogoCounter.incr(null, 1L);
				}
			}
		}
	}

	@Override
	public void denyLogo(long uid) {
		Profile updateProfile = new Profile();
		updateProfile.setUid(uid);
		updateProfile.setLogoVerifyState(LogoVerifyState.UNVERIFIED.getType());
		if (profileMapper.updateByPrimaryKeySelective(updateProfile) > 0) {
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(uid);
			if (null != profileCache) {
				profileService.clearProfileCache(uid);
				dialogService.sendOfficialSMS(uid,
						DialogContentTemplate.DENY_LOGO,
						profileCache.getNickname());
			}
		}
	}

	@Override
	public void removeLogo(long uid) {
		PostExample postExample = new PostExample();
		postExample.createCriteria().andCreateUidEqualTo(uid)
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
				.andDefunctEqualTo(false);
		Post post = new Post();
		post.setVerifyType(VerifyType.SHIELD.getType());
		post.setLastModifyTime(new Date());
		postMapper.updateByExampleSelective(post, postExample);

		Profile profile = profileMapper.selectByPrimaryKey(uid);
		profile.setLogoPic(null);
		profile.setLastUpdateTime(null);
		profile.setLogoVerifyState(LogoVerifyState.UNVERIFIED.getType());
		profileMapper.updateByPrimaryKey(profile);
		redisTemplate.delete(RedisKeyGenerator.genUserLatestPostKey(uid));

		profileService.clearProfileCache(uid);
		dialogService.sendOfficialSMS(uid, DialogContentTemplate.DENY_LOGO);
	}
}
