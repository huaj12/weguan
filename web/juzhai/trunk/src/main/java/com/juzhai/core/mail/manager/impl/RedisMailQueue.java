package com.juzhai.core.mail.manager.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.manager.MailQueue;

@Component
public class RedisMailQueue implements MailQueue {

	@Autowired
	private RedisTemplate<String, Mail> redisTemplate;

	@Override
	public void push(String name, Mail mail) {
		redisTemplate.opsForList().rightPush(
				RedisKeyGenerator.genMailQueueKey(name), mail);
	}

	@Override
	public void pushWithPriotity(String name, Mail mail, int priority) {
		throw new UnsupportedOperationException("none implement");
	}

	@Override
	public Mail pop(String name) {
		return redisTemplate.opsForList().leftPop(
				RedisKeyGenerator.genMailQueueKey(name));
	}

	@Override
	public Mail blockPop(String name, int timeout) {
		return redisTemplate.opsForList().leftPop(
				RedisKeyGenerator.genMailQueueKey(name), timeout,
				TimeUnit.SECONDS);
	}

}
