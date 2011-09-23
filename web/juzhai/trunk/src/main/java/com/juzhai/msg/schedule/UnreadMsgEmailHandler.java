package com.juzhai.msg.schedule;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;

@Component
public class UnreadMsgEmailHandler extends AbstractScheduleHandler {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MailManager mailManager;

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
				int unReadMsgCount = unReadMsgCount(profile.getUid());
				if (unReadMsgCount > 0) {
					// TODO build prop
					Mail mail = MailFactory.create(profile.getEmail(),
							profile.getNickname(), true);
					mail.buildSubject("", null);
					mail.buildText("", null);
					mailManager.sendMail(mail, false);
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

	private int unReadMsgCount(long uid) {
		// TODO 获取未读数
		return 0;
	}
}
