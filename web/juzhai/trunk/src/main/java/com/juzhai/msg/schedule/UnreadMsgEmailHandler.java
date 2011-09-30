package com.juzhai.msg.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.controller.view.ActMsgView;
import com.juzhai.msg.service.IActMsgService;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;

@Component
public class UnreadMsgEmailHandler extends AbstractScheduleHandler {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private IActMsgService actMsgService;
	@Autowired
	private ITpUserService tpUserService;
	@Value("${unreadEmail.msg.maxResults}")
	private int unreadEmailMsgMaxResults = 5;

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
				long unReadMsgCount = unReadMsgCount(profile.getUid());
				if (unReadMsgCount > 0) {
					Mail mail = MailFactory.create(profile.getEmail(),
							profile.getNickname(), true);
					mail.buildSubject("/mail/unreadMsg/subject.vm",
							buildSubjectProp(profile));
					mail.buildText("/mail/unreadMsg/content.vm",
							buildContentProp(profile, unReadMsgCount));
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

	private long unReadMsgCount(long uid) {
		return actMsgService.countUnRead(uid);
	}

	private List<ActMsgView> getActMsgViewList(long uid) {
		List<ActMsg> actMsgList = actMsgService.pageUnRead(uid, 0,
				unreadEmailMsgMaxResults);
		List<ActMsgView> actMsgViewList = new ArrayList<ActMsgView>(
				actMsgList.size());
		for (ActMsg actMsg : actMsgList) {
			ActMsgView actMsgView = new ActMsgView();
			actMsgView.setProfileCache(profileService
					.getProfileCacheByUid(actMsg.getUid()));
			actMsgView.setMsgType(actMsg.getType());
			actMsgView.setDate(actMsg.getDate());
			actMsgViewList.add(actMsgView);
		}
		return actMsgViewList;
	}

	private Map<String, Object> buildSubjectProp(Profile profile) {
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("nickname", profile.getNickname());
		return prop;
	}

	private Map<String, Object> buildContentProp(Profile profile, long count) {
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("profile", profile);
		TpUser tpUser = tpUserService.getTpUserByUid(profile.getUid());
		if (null != tpUser) {
			Thirdparty tp = com.juzhai.passport.InitData
					.getTpByTpNameAndJoinType(tpUser.getTpName(),
							JoinTypeEnum.APP);
			if (null != tp) {
				prop.put("tp", tp);
			}
		}
		try {
			prop.put("token", DESUtils.encryptToHexString(profileService
					.getUserSecretKey(profile.getUid()),
					String.valueOf(profile.getUid()).getBytes()));
		} catch (Exception e) {
			log.error("encrypt uid error.[uid=" + profile.getUid() + "]", e);
		}
		prop.put("msgCount", count);
		List<ActMsgView> actMsgViewList = getActMsgViewList(profile.getUid());
		prop.put("actMsgViewList", actMsgViewList);

		return prop;
	}
}
