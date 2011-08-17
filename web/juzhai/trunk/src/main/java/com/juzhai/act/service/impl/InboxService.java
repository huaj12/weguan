package com.juzhai.act.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreGenerator;
import com.juzhai.act.service.IInboxService;
import com.juzhai.core.cache.RedisKeyGenerator;

@Service
public class InboxService implements IInboxService {

	private static final String VALUE_DELIMITER = "|";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private IScoreGenerator inboxScoreGenerator;

	// @Autowired
	// private int inboxCapacity = 100;

	@Override
	public void push(long receiverId, long senderId, long actId) {
		String key = RedisKeyGenerator.genInboxActsKey(receiverId);
		redisTemplate.opsForZSet().add(key, assembleValue(senderId, actId),
				inboxScoreGenerator.genScore(senderId, receiverId, actId));
		// TODO 删除超过100个的值
		// int overCount = redisTemplate.opsForZSet().size(key).intValue()
		// - inboxCapacity;
		// if (overCount > 0) {
		// redisTemplate.opsForZSet().removeRange(key, 0, overCount - 1);
		// }
	}

	private String assembleValue(long senderId, long actId) {
		return senderId + VALUE_DELIMITER + actId;
	}
}
