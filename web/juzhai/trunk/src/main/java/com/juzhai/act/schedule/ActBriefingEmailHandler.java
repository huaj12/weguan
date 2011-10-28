package com.juzhai.act.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IActChartsService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.Feed.FeedType;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;

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
	@Autowired
	private IActChartsService actChartsService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IActService actService;
	@Value("${briefing.userAct.maxResults}")
	private int briefingUserActMaxResults = 5;
	@Value("${briefing.userAct.moveUp.days}")
	private int briefingUserActMoveUpDays = 14;

	@Override
	protected void doHandle() {
		// 获取排行榜
		Map<Integer, List<Act>> actChartsMap = actChartsService.showActCharts();
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
						Mail mail = MailFactory.create(profile.getEmail(),
								profile.getNickname(), true);
						mail.buildSubject("/mail/briefing/subject.vm",
								buildSubjectProp(profile));
						mail.buildText(
								"/mail/briefing/content.vm",
								buildContentProp(profile, count, userActs,
										actChartsMap));
						mailManager.sendMail(mail, false);
					}
				}
			}
			firstResult += maxResults;
		}
		stopMailDaemon();
	}

	private void startMailDaemon() {
		mailManager.startDaemon();
	}

	private void stopMailDaemon() {
		mailManager.startDaemon();
	}

	private Map<String, Object> buildSubjectProp(Profile profile) {
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("nickname", profile.getNickname());
		return prop;
	}

	private Map<String, Object> buildContentProp(Profile profile, int count,
			List<UserAct> userActList, Map<Integer, List<Act>> actChartsMap) {
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
		prop.put("feedCount", count);
		List<Feed> feedList = new ArrayList<Feed>();
		for (UserAct userAct : userActList) {
			ProfileCache profileCache = profileService
					.getProfileCacheByUid(userAct.getUid());
			Act act = actService.getActById(userAct.getActId());
			if (null != profileCache && null != act) {
				feedList.add(new Feed(profileCache, FeedType.SPECIFIC, userAct
						.getLastModifyTime(), act));
			}
		}
		prop.put("feedList", feedList);
		for (Map.Entry<Integer, List<Act>> entry : actChartsMap.entrySet()) {
			prop.put("actCharts_" + entry.getKey(), entry.getValue());
		}

		return prop;
	}
}
