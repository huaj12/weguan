package com.juzhai.notice.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IEmailService;
import com.juzhai.passport.service.IProfileService;

@Component
public class NoticeEmailHandler extends AbstractScheduleHandler {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MailManager simpleMailManager;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IEmailService emailService;

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
				if (!profile.getSubEmail()
						|| StringUtils.isEmpty(profile.getEmail())) {
					continue;
				}
				Map<Integer, Long> noticeNumMap = noticeService
						.getAllNoticeNum(profile.getUid());
				for (NoticeType noticeType : NoticeType.values()) {
					if (!emailService.isSubNotice(profile.getUid(), noticeType)) {
						noticeNumMap.remove(noticeType.getType());
					}
				}
				Map<String, Object> params = new HashMap<String, Object>();
				for (Map.Entry<Integer, Long> entry : noticeNumMap.entrySet()) {
					if (null != entry.getValue() && entry.getValue() > 0) {
						params.put(entry.getKey().toString(), entry.getValue());
					}
				}
				if (MapUtils.isNotEmpty(params)) {
					Mail mail = MailFactory.create(profile.getEmail(),
							profile.getNickname(), true);
					mail.buildSubject("/mail/notice/subject.vm",
							buildSubjectProp(profile));
					params.put("nickname", profile.getNickname());
					mail.buildText("/mail/notice/content.vm", params);
					simpleMailManager.sendMail(mail, false);
				}
			}
			firstResult += maxResults;
		}
		stopMailDaemon();
	}

	private void startMailDaemon() {
		simpleMailManager.startDaemon();
	}

	private void stopMailDaemon() {
		simpleMailManager.stopDaemon();
	}

	private Map<String, Object> buildSubjectProp(Profile profile) {
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("nickname", profile.getNickname());
		return prop;
	}
}
