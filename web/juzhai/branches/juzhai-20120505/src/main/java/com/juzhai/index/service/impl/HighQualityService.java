package com.juzhai.index.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.index.service.IHighQualityService;

@Service
public class HighQualityService implements IHighQualityService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	public void addHighQuality(long uid) {
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genHighQualityUsersKey(), uid,
				System.currentTimeMillis());
	}

	@Override
	public void removeHighQuality(long uid) {
		redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genHighQualityUsersKey(), uid);
	}

	@Override
	public List<Long> highQualityUsers(int firstResult, int maxResults) {
		Set<Long> uids = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genHighQualityUsersKey(), firstResult,
				firstResult + maxResults - 1);
		if (CollectionUtils.isEmpty(uids)) {
			return Collections.emptyList();
		} else {
			return new ArrayList<Long>(uids);
		}
	}

	@Override
	public int countHighQualityUsers() {
		return redisTemplate.opsForZSet()
				.size(RedisKeyGenerator.genHighQualityUsersKey()).intValue();
	}

	@Override
	public boolean isHighQuality(long uid) {
		return redisTemplate.opsForZSet().score(
				RedisKeyGenerator.genHighQualityUsersKey(), uid) != null;
	}

}
