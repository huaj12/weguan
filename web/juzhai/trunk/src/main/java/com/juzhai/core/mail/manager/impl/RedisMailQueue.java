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
	public void push(Mail mail) {
		redisTemplate.opsForList().rightPush(
				RedisKeyGenerator.genMailQueueKey(), mail);
	}

	@Override
	public void pushWithPriotity(Mail mail, int priority) {
		throw new UnsupportedOperationException("none implement");
	}

	@Override
	public Mail pop() {
		return redisTemplate.opsForList().leftPop(
				RedisKeyGenerator.genMailQueueKey());
	}

	@Override
	public Mail blockPop(int timeout) {
		return redisTemplate.opsForList().leftPop(
				RedisKeyGenerator.genMailQueueKey(), timeout, TimeUnit.SECONDS);
	}

}
