package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.stats.counter.service.ICounter;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.exception.InterestUserException;
import com.juzhai.home.mapper.InterestUserMapper;
import com.juzhai.home.model.InterestUser;
import com.juzhai.home.model.InterestUserExample;
import com.juzhai.home.service.IDialogService;
import com.juzhai.home.service.IInterestUserService;
import com.juzhai.passport.bean.ProfileCache;
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
	private IDialogService dialogService;
	@Autowired
	private ICounter interestUserCounter;

	@Override
	public void interestUser(long uid, long targetUid)
			throws InterestUserException {
		interestUser(uid, targetUid, "");
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

	@Override
	public List<Long> interestUids(long uid) {
		Set<Long> uids = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genInterestUsersKey(uid));
		if (CollectionUtils.isNotEmpty(uids)) {
			return new ArrayList<Long>(uids);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void interestUser(long uid, long targetUid, String content)
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
		// redis
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genInterestUsersKey(uid), targetUid);
		// 发送私信
		try {
			dialogService.sendSMS(uid, targetUid,
					DialogContentTemplate.INTEREST_USER, content);
		} catch (DialogException e) {
		}

		interestUserCounter.incr(null, 1L);
	}

}
