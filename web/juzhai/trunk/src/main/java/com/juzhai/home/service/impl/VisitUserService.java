package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.controller.view.VisitorView;
import com.juzhai.home.service.IVisitUserService;
import com.juzhai.passport.service.IProfileService;

@Service
public class VisitUserService implements IVisitUserService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;

	@Override
	public void addVisitUser(long uid, long visitUid) {
		String key = RedisKeyGenerator.genVisitUsersKey(uid);
		redisTemplate.opsForZSet().add(key, visitUid,
				System.currentTimeMillis());
		// redisTemplate.opsForZSet().removeRange(key, -100, 0);
	}

	@Override
	public List<VisitorView> listVisitUsers(long uid, int firstResult,
			int maxResults) {
		List<VisitorView> visitorViewList = new ArrayList<VisitorView>();
		Set<TypedTuple<Long>> users = redisTemplate.opsForZSet()
				.reverseRangeWithScores(
						RedisKeyGenerator.genVisitUsersKey(uid), firstResult,
						firstResult + maxResults - 1);
		for (TypedTuple<Long> user : users) {
			VisitorView view = new VisitorView();
			view.setProfileCache(profileService.getProfileCacheByUid(user
					.getValue()));
			view.setVisitTime(new Date(user.getScore().longValue()));
			visitorViewList.add(view);
		}
		return visitorViewList;
	}

	@Override
	public int countVisitUsers(long uid) {
		return redisTemplate.opsForZSet()
				.size(RedisKeyGenerator.genVisitUsersKey(uid)).intValue();
	}
}
