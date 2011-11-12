package com.juzhai.index.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.index.controller.view.ActLiveView;
import com.juzhai.index.service.IActLiveService;
import com.juzhai.passport.service.IProfileService;

@Service
public class ActLiveService implements IActLiveService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IActService actService;

	@Override
	public void addNewLive(long uid, long actId, Date time) {
		if (time == null) {
			time = new Date();
		}
		redisTemplate.opsForZSet().add(RedisKeyGenerator.genActivistsKey(),
				uid, time.getTime());
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genUserNewestActKey(uid), actId);
	}

	@Override
	public void removeLive(long uid, long actId) {
		if (redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserNewestActKey(uid)) == actId) {
			// 删除并更新最新的
			Set<TypedTuple<Long>> actIds = redisTemplate.opsForZSet()
					.reverseRangeWithScores(
							RedisKeyGenerator.genMyActsKey(uid), 0, 0);
			if (CollectionUtils.isNotEmpty(actIds)) {
				// 直接更新
				for (TypedTuple<Long> typedTuple : actIds) {
					addNewLive(uid, typedTuple.getValue(), new Date(typedTuple
							.getScore().longValue()));
				}
			} else {
				// 删除
				redisTemplate.opsForZSet().remove(
						RedisKeyGenerator.genActivistsKey(), uid);
				redisTemplate
						.delete(RedisKeyGenerator.genUserNewestActKey(uid));
			}
		}

	}

	@Override
	public List<ActLiveView> listActivists(int firstResult, int maxResults) {
		List<ActLiveView> actLiveViewList = new ArrayList<ActLiveView>();
		Set<TypedTuple<Long>> users = redisTemplate.opsForZSet()
				.reverseRangeWithScores(RedisKeyGenerator.genActivistsKey(),
						firstResult, firstResult + maxResults - 1);
		for (TypedTuple<Long> user : users) {
			actLiveViewList.add(new ActLiveView(profileService
					.getProfileCacheByUid(user.getValue()),
					getUserNewestAct(user.getValue()), new Date(user.getScore()
							.longValue())));
		}
		return actLiveViewList;
	}

	private Act getUserNewestAct(long uid) {
		long actId = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genUserNewestActKey(uid));
		return actService.getActById(actId);
	}

	@Override
	public int countActivists() {
		return redisTemplate.opsForZSet()
				.size(RedisKeyGenerator.genActivistsKey()).intValue();
	}
}
