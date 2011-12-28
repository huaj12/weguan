package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.InterestUserException;
import com.juzhai.passport.mapper.InterestUserMapper;
import com.juzhai.passport.model.InterestUser;
import com.juzhai.passport.model.InterestUserExample;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;

@Service
public class InterestUserService implements IInterestUserService {

	@Autowired
	private InterestUserMapper interestUserMapper;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private INoticeService noticeService;

	@Override
	public void interestUser(long uid, long targetUid)
			throws InterestUserException {
		InterestUserExample example = new InterestUserExample();
		example.createCriteria().andUidEqualTo(uid)
				.andInterestUidEqualTo(targetUid);
		if (uid == targetUid
				|| profileService.getProfileCacheByUid(targetUid) == null) {
			throw new InterestUserException(
					InterestUserException.ILLEGAL_OPERATION);
		}
		if (interestUserMapper.countByExample(example) > 0) {
			throw new InterestUserException(
					InterestUserException.INTEREST_USER_EXISTENCE);
		}
		// 添加
		InterestUser interestUser = new InterestUser();
		interestUser.setUid(uid);
		interestUser.setInterestUid(targetUid);
		interestUser.setCreateTime(new Date());
		interestUser.setLastModifyTime(interestUser.getCreateTime());
		interestUserMapper.insertSelective(interestUser);
		// TODO redis
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genInterestUsersKey(uid), targetUid);
		noticeService.incrNotice(targetUid, NoticeType.INTERESTME);
	}

	@Override
	public void removeInterestUser(long uid, long targetUid) {
		InterestUserExample example = new InterestUserExample();
		example.createCriteria().andUidEqualTo(uid)
				.andInterestUidEqualTo(targetUid);
		if (interestUserMapper.deleteByExample(example) > 0) {
			redisTemplate.opsForSet().remove(
					RedisKeyGenerator.genInterestUsersKey(uid), targetUid);
		}
	}

	@Override
	public List<ProfileCache> listInterestUser(long uid, int firstResult,
			int maxResults) {
		InterestUserExample example = new InterestUserExample();
		example.createCriteria().andUidEqualTo(uid);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc, id desc");
		List<InterestUser> list = interestUserMapper.selectByExample(example);
		List<ProfileCache> profileCacheList = new ArrayList<ProfileCache>();
		for (InterestUser interestUser : list) {
			profileCacheList.add(profileService
					.getProfileCacheByUid(interestUser.getInterestUid()));
		}
		return profileCacheList;
	}

	@Override
	public int countInterestUser(long uid) {
		InterestUserExample example = new InterestUserExample();
		example.createCriteria().andUidEqualTo(uid);
		return interestUserMapper.countByExample(example);
	}

	@Override
	public List<ProfileCache> listInterestMeUser(long uid, int firstResult,
			int maxResults) {
		InterestUserExample example = new InterestUserExample();
		example.createCriteria().andInterestUidEqualTo(uid);
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc, id desc");
		List<InterestUser> list = interestUserMapper.selectByExample(example);
		List<ProfileCache> profileCacheList = new ArrayList<ProfileCache>();
		for (InterestUser interestUser : list) {
			profileCacheList.add(profileService
					.getProfileCacheByUid(interestUser.getUid()));
		}
		return profileCacheList;
	}

	@Override
	public int countInterestMeUser(long uid) {
		InterestUserExample example = new InterestUserExample();
		example.createCriteria().andInterestUidEqualTo(uid);
		return interestUserMapper.countByExample(example);
	}

	@Override
	public boolean isInterest(long uid, long targetUid) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genInterestUsersKey(uid), targetUid);
	}

}
