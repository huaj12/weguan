package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.service.IBlacklistService;
import com.juzhai.passport.exception.InputBlacklistException;

@Service
public class BlacklistService implements IBlacklistService {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	public void shield(long createUid, long shieldUid)
			throws InputBlacklistException {
		if (createUid == 0 || shieldUid == 0 || shieldUid == createUid) {
			throw new InputBlacklistException(
					InputBlacklistException.ILLEGAL_OPERATION);
		}
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genBlacklistKey(createUid), shieldUid,
				System.currentTimeMillis());

	}

	@Override
	public void cancel(long createUid, long shieldUid)
			throws InputBlacklistException {
		if (createUid == 0 || shieldUid == 0 || shieldUid == createUid) {
			throw new InputBlacklistException(
					InputBlacklistException.ILLEGAL_OPERATION);
		}
		redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genBlacklistKey(createUid), shieldUid);

	}

	@Override
	public List<Long> blacklist(long createUid, int firstResult, int maxResults) {
		Set<Long> ids = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genBlacklistKey(createUid), firstResult,
				firstResult + maxResults - 1);
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return new ArrayList<Long>(ids);
	}

	@Override
	public boolean isShield(long createUid, long shieldUid) {
		return redisTemplate.opsForZSet().score(
				RedisKeyGenerator.genBlacklistKey(createUid), shieldUid) == null ? false
				: true;
	}

	@Override
	public int countBlacklist(long createUid) {
		Long count = redisTemplate.opsForZSet().size(
				RedisKeyGenerator.genBlacklistKey(createUid));
		return count == null ? 0 : count.intValue();
	}
}
