package com.juzhai.home.schedule;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;

//@Component
public class ResetQuestionUsersHandler extends AbstractScheduleHandler {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	protected void doHandle() {
		Set<String> keys = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genQuestionUserKeysKey());
		if (!CollectionUtils.isEmpty(keys)) {
			redisTemplate.delete(keys);
			redisTemplate.delete(RedisKeyGenerator.genQuestionUserKeysKey());
		}
	}
}
