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
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.IProfileSearchService;
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
	private IProfileSearchService profileSearchService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IUserGuideService userGuideService;
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
		boolean falg = false;
		if (profileService.isValidLogo(uid)) {
			falg = true;
		}
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
					// 后台通过头像
					// TODO (done)阳仔啊，要仔细啊！！！！
					// 有对照mmap来加的吗？你自己提出的可能是修改头像，这里有判断了吗？
					if (userGuideService.isCompleteGuide(uid)) {
						if (!falg) {
							profileSearchService.createIndex(uid);
						}
					}
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
		boolean flag = false;
		if (profileService.isValidUser(uid)) {
			flag = true;
		}

		profile.setLogoPic(null);
		profile.setLastUpdateTime(null);
		profile.setLogoVerifyState(LogoVerifyState.UNVERIFIED.getType());
		profileMapper.updateByPrimaryKey(profile);
		redisTemplate.delete(RedisKeyGenerator.genUserLatestPostKey(uid));

		profileService.clearProfileCache(uid);
		dialogService.sendOfficialSMS(uid, DialogContentTemplate.DENY_LOGO);
		// 删除头像
		// TODO (done) 会不会存在没有必要去删索引的情况？
		if (flag) {
			profileSearchService.deleteIndex(uid);
		}
		// 删除头像的用户的所有通过拒宅
		List<Post> posts = postService.findAllPost(uid);
		for (Post p : posts) {
			try {
				postService.shieldPost(p.getId());
			} catch (InputPostException e) {
			}
		}
	}
}
