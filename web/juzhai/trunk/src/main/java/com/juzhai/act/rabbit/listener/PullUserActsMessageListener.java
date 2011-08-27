package com.juzhai.act.rabbit.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.act.caculator.IScoreGenerator;
import com.juzhai.act.model.UserAct;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.msg.rabbit.message.MsgMessage;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Component
public class PullUserActsMessageListener implements
		IRabbitMessageListener<UserAct, Object> {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IScoreGenerator inboxScoreGenerator;
	// @Autowired
	private int inboxCapacity = 100;

	@Override
	public Object handleMessage(UserAct userAct) {
		// Assert.notNull(userAct, "Object userAct must not be null.");
		// Assert.notNull(userAct.getId(), "UserAct's id must not be null.");
		// Assert.notNull(userAct.getUid(), "UserAct's uid must not be null");
		// Assert.notNull(userAct.getActId(),
		// "UserAct's actId must not be null");
		// Assert.notNull(userAct.getHotLev(),
		// "UserAct's hotLev must not be null");
		// // save my act to redis
		// redisTemplate.opsForZSet().add(
		// RedisKeyGenerator.genMyActsKey(userAct.getUid()),
		// userAct.getActId(), userAct.getHotLev());
		//
		// // push to friends
		// Set<Long> targets = getPushTargets(userAct.getUid());
		// for (Long targetId : targets) {
		// String key = RedisKeyGenerator.genInboxActsKey(targetId);
		// // TODO 使用piple
		// redisTemplate.opsForZSet().add(
		// key,
		// userAct.getId(),
		// inboxScoreGenerator.genScore(userAct.getUid(), targetId,
		// userAct.getActId()));
		// // 删除超过100个的值
		// int overCount = redisTemplate.opsForZSet().size(key).intValue()
		// - inboxCapacity;
		// if (overCount > 0) {
		// redisTemplate.opsForZSet().removeRange(key, 0, overCount - 1);
		// }
		// }
		//
		// return null;
		// }
		//
		// private Set<Long> getPushTargets(long uid) {
		// Set<Long> targets = friendService.getAppFriends(uid);
		// for (Profile profile : getSameCityUsers(uid)) {
		// if (!targets.contains(profile.getUid())) {
		// targets.add(profile.getUid());
		// }
		// }
		// return targets;
		// }
		//
		// private List<Profile> getSameCityUsers(long uid) {
		// long cityId = profileService.getUserCityFromCache(uid);
		// if (cityId <= 0) {
		// return Collections.emptyList();
		// }
		// return profileService.getProfilesByCityId(cityId);
	
		return null;
	}
}
