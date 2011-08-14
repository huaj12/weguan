package com.juzhai.act.rabbit.listener;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.juzhai.act.model.UserAct;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Component
public class UpdateActMessageListener implements
		IRabbitMessageListener<UserAct, Object> {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IFriendService friendService;

	@Override
	public Object handleMessage(UserAct userAct) {
		Assert.notNull(userAct, "Object userAct must not be null.");
		Assert.notNull(userAct.getUid(), "UserAct's uid must not be null");
		Assert.notNull(userAct.getActId(), "UserAct's actId must not be null");
		Assert.notNull(userAct.getHotLev(), "UserAct's hotLev must not be null");
		// save my act to redis
		redisTemplate.opsForZSet().add(
				RedisKeyGenerator.genMyActsKey(userAct.getUid()),
				userAct.getActId(), userAct.getHotLev());

		// TODO push to friends
		Set<Long> targets = getPushTargets(userAct.getUid());
		for (Long targetId : targets) {
			// TODO 使用piple
			redisTemplate.opsForZSet().add(
					RedisKeyGenerator.genInboxActsKey(targetId),
					userAct.getActId(), 0);
			// TODO 删除超过100个的值
		}

		return null;
	}

	private Set<Long> getPushTargets(long uid) {
		Set<Long> targets = friendService.getAppFriends(uid);
		for (Profile profile : getSameCityUsers(uid)) {
			if (!targets.contains(profile.getUid())) {
				targets.add(profile.getUid());
			}
		}
		return targets;
	}

	private List<Profile> getSameCityUsers(long uid) {
		long cityId = profileService.getUserCityFromCache(uid);
		if (cityId <= 0) {
			return Collections.emptyList();
		}
		return profileService.getProfilesByCityId(cityId);
	}
}
