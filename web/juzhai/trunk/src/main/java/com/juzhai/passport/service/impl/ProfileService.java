/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.KeyGenerator;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;

@Service
public class ProfileService implements IProfileService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;

	@Override
	public void cacheUserCity(long uid) {
		// 初始化所在地等需要匹配的信息（redis）
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		if (null != profile && profile.getCity() != null) {
			redisTemplate.opsForValue().set(KeyGenerator.genUserCityKey(uid),
					profile.getCity());
		}

	}

	@Override
	public long getUserCityFromCache(long uid) {
		return redisTemplate.opsForValue()
				.get(KeyGenerator.genUserCityKey(uid));
	}

}
