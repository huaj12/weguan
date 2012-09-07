package com.juzhai.core.stats.counter.server;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisCounterServer implements ICounterServer {

	private RedisTemplate<String, Long> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, Long> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void incr(String key, long value) {
		if (value > 0) {
			redisTemplate.opsForValue().increment(key, value);
		}
	}

	@Override
	public void decr(String key, long value) {
		if (value < 0) {
			redisTemplate.opsForValue().increment(key, value);
		}
	}

	@Override
	public long get(String key) {
		return redisTemplate.opsForValue().increment(key, 0);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

}
