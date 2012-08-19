package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.douban.DoumailEntry;
import com.google.gdata.data.douban.MiniblogEntry;
import com.google.gdata.data.douban.MiniblogFeed;
import com.google.gdata.data.douban.UserEntry;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class DoubanConnectSynchronizeService implements ISynchronizeService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserService tpUserService;

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		send(authInfo, text + link);
	}

	private void send(AuthInfo authInfo, String text) {
		try {
			Thirdparty tp = InitData.getTpByTpNameAndJoinType(
					authInfo.getThirdpartyName(),
					JoinTypeEnum.getJoinTypeEnum(authInfo.getJoinType()));
			DoubanService doubanService = DoubanService.getDoubanService(
					authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getAppKey(), authInfo.getAppSecret(),
					tp.getAppId());
			doubanService.createSaying(new PlainTextConstruct(text));
		} catch (Exception e) {
			log.error("douban connect sendMessage is error. " + e.getMessage(),
					e);
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {
		if (CollectionUtils.isEmpty(fuids)) {
			return;
		}
		String userIds = "";
		for (String fuid : fuids) {
			userIds += "@" + fuid + " ";
		}
		send(authInfo, userIds + text);
	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {
		if (ArrayUtils.isEmpty(fuids)) {
			return;
		}
		for (String fuid : fuids) {
			try {
				Thirdparty tp = InitData.getTpByTpNameAndJoinType(
						authInfo.getThirdpartyName(),
						JoinTypeEnum.getJoinTypeEnum(authInfo.getJoinType()));
				DoubanService doubanService = DoubanService.getDoubanService(
						authInfo.getToken(), authInfo.getTokenSecret(),
						authInfo.getAppKey(), authInfo.getAppSecret(),
						tp.getAppId());
				UserEntry entry = doubanService.getUser(fuid);
				DoumailEntry doumail = doubanService.sendDoumail(
						new PlainTextConstruct(text.trim()),
						new PlainTextConstruct(text.trim()), entry);
			} catch (Exception e) {
				log.error("connect douban sendSysMessage is error."
						+ e.getMessage() + "fuid:" + fuid);
			}
		}

	}

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		try {
			Thirdparty tp = InitData.getTpByTpNameAndJoinType(
					authInfo.getThirdpartyName(),
					JoinTypeEnum.getJoinTypeEnum(authInfo.getJoinType()));
			DoubanService doubanService = DoubanService.getDoubanService(
					authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getAppKey(), authInfo.getAppSecret(),
					tp.getAppId());
			MiniblogFeed miniblogFeed = doubanService.getUserMiniblogs(
					authInfo.getTpIdentity(), 1, size);
			List<MiniblogEntry> status = miniblogFeed.getEntries();
			if (CollectionUtils.isNotEmpty(status)) {
				for (MiniblogEntry s : status) {
					UserStatus userStatus = new UserStatus();
					userStatus.setContent(((TextContent) s.getContent())
							.getContent().getPlainText());
					userStatus.setTime(new Date(s.getPublished().getValue()));
					userStatusList.add(userStatus);
				}
			}
		} catch (Exception e) {
			log.error("douban connect listStatus is error. " + e.getMessage(),
					e);
		}
		return userStatusList;
	}

}
