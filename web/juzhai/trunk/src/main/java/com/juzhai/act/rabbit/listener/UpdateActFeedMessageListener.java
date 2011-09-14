package com.juzhai.act.rabbit.listener;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.UserAct;
import com.juzhai.act.rabbit.message.ActUpdateMessage;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.service.IFriendService;

@Component
public class UpdateActFeedMessageListener implements
		IRabbitMessageListener<ActUpdateMessage, Object> {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IInboxService inboxService;

	@Override
	public Object handleMessage(ActUpdateMessage actUpdateMessage) {
		if (!checkMessage(actUpdateMessage)) {
			return null;
		}
		UserAct userAct = actUpdateMessage.getBody();
		// push to friends
		Set<Long> targets = getPushTargets(userAct.getUid());
		for (Long targetId : targets) {
			// TODO 使用piple
			inboxService.push(targetId, userAct.getUid(), userAct.getActId());
		}

		return null;
	}

	private boolean checkMessage(ActUpdateMessage message) {
		if (null == message) {
			log.error("Message is null.");
			return false;
		}
		UserAct userAct = message.getBody();
		if (null == userAct) {
			log.error("Object userAct must not be null.");
			return false;
		}
		if (null == userAct.getId()) {
			log.error("UserAct's id must not be null.");
			return false;
		}
		if (null == userAct.getUid()) {
			log.error("UserAct's uid must not be null");
			return false;
		}
		if (null == userAct.getActId()) {
			log.error("UserAct's actId must not be null");
			return false;
		}
		return true;
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
