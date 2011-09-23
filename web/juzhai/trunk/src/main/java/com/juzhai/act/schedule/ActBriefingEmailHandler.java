package com.juzhai.act.schedule;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Component
public class ActBriefingEmailHandler extends AbstractScheduleHandler {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IUserActService userActService;
	@Value("${briefing.userAct.maxResults}")
	private int briefingUserActMaxResults = 5;
	@Value("${briefing.userAct.moveUp.days}")
	private int briefingUserActMoveUpDays = 14;

	@Override
	protected void doHandle() {
		startMailDaemon();
		int firstResult = 0;
		int maxResults = 200;
		while (true) {
			List<Profile> profileList = profileService.getSubEmailProfiles(
					firstResult, maxResults);
			if (CollectionUtils.isEmpty(profileList)) {
				break;
			}
			for (Profile profile : profileList) {
				Set<Long> friendIds = friendService.getAppFriends(profile
						.getUid());
				Date startDate = DateUtils.addDays(new Date(),
						-briefingUserActMoveUpDays);
				int count = userActService.countFriendsRecentAct(friendIds,
						startDate);
				if (count > 0) {
					List<UserAct> userActs = userActService
							.listFriendsRecentAct(friendIds, startDate, 0,
									briefingUserActMaxResults);
					if (CollectionUtils.isNotEmpty(userActs)) {
						// TODO build prop
						Mail mail = MailFactory.create(profile.getEmail(),
								profile.getNickname(), true);
						mail.buildSubject("", null);
						mail.buildText("", null);
						mailManager.sendMail(mail, false);
					}
				}
			}
		}
		stopMailDaemon();
	}

	private void startMailDaemon() {
		mailManager.startDaemon();
	}

	private void stopMailDaemon() {
		mailManager.startDaemon();
	}
}
