package com.juzhai.promotion.service.impl;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.passport.LockLevelConfig;
import com.juzhai.promotion.service.IPromotionService;

@Service
public class PromotionService implements IPromotionService {
	private static final Log log = LogFactory.getLog(LockLevelConfig.class);

	private static final String OCCASIONAL_CONFIG_PATH = "properties/occasional.properties";
	private static final String USER_MAN_CONFIG_PATH = "properties/nicemanuser.properties";
	private static final String USER_WOMEN_CONFIG_PATH = "properties/nicewomenuser.properties";
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, String> redisStringTemplate;

	@Override
	public void initMan() {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(USER_MAN_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String value = (String) entry.getKey();
			redisTemplate.delete(RedisKeyGenerator.genNiceUser(1));
			redisTemplate.opsForSet().add(RedisKeyGenerator.genNiceUser(1),
					Long.parseLong(value));
		}
	}

	@Override
	public void initWomen() {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(USER_WOMEN_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String value = (String) entry.getKey();
			redisTemplate.delete(RedisKeyGenerator.genNiceUser(0));
			redisTemplate.opsForSet().add(RedisKeyGenerator.genNiceUser(0),
					Long.parseLong(value));
		}
	}

	@Override
	public void initOccasionalAddress() {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(OCCASIONAL_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String value = (String) entry.getKey();
			redisStringTemplate
					.delete(RedisKeyGenerator.genOccasionalAddress());
			redisStringTemplate.opsForSet().add(
					RedisKeyGenerator.genOccasionalAddress(), value);
		}
	}
}
