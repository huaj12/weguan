package com.juzhai.cms.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.IVerifyLogoService;
import com.juzhai.core.dao.Limit;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.service.IDialogService;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;
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
	@Value("${official.notice.uid}")
	private long officialNoticeUid;

	@Override
	public List<Profile> listVerifyLogoProfile(LogoVerifyState logoVerifyState,
			int firstResult, int maxResult) {
		ProfileExample example = new ProfileExample();
		example.createCriteria().andNewLogoPicNotEqualTo(StringUtils.EMPTY)
				.andNewLogoPicIsNotNull()
				.andLogoVerifyStateEqualTo(logoVerifyState.getType());
		example.setLimit(new Limit(firstResult, maxResult));
		example.setOrderByClause("last_modify_time asc");
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
					dialogService.sendSMS(officialNoticeUid, uid,
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
				dialogService.sendSMS(officialNoticeUid, uid,
						DialogContentTemplate.DENY_LOGO,
						profileCache.getNickname());
			}
		}
	}
}
