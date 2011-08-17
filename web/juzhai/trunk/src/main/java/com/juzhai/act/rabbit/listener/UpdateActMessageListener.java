package com.juzhai.act.rabbit.listener;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IInboxService;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.passport.service.IFriendService;

@Component
public class UpdateActMessageListener implements
		IRabbitMessageListener<UserAct, Object> {

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IInboxService inboxService;

	@Override
	public Object handleMessage(UserAct userAct) {
		Assert.notNull(userAct, "Object userAct must not be null.");
		Assert.notNull(userAct.getId(), "UserAct's id must not be null.");
		Assert.notNull(userAct.getUid(), "UserAct's uid must not be null");
		Assert.notNull(userAct.getActId(), "UserAct's actId must not be null");

		// push to friends
		Set<Long> targets = getPushTargets(userAct.getUid());
		for (Long targetId : targets) {
			// TODO 使用piple
			inboxService.push(targetId, userAct.getUid(), userAct.getActId());
		}

		return null;
	}

	private Set<Long> getPushTargets(long uid) {
		Set<Long> targets = friendService.getAppFriends(uid);
		// for (Profile profile : getSameCityUsers(uid)) {
		// if (!targets.contains(profile.getUid())) {
		// targets.add(profile.getUid());
		// }
		// }
		return targets;
	}

	// private List<Profile> getSameCityUsers(long uid) {
	// long cityId = profileService.getUserCityFromCache(uid);
	// if (cityId <= 0) {
	// return Collections.emptyList();
	// }
	// return profileService.getProfilesByCityId(cityId);
	// }
}
