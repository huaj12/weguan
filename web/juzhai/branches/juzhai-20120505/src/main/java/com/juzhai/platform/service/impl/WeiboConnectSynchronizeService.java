package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;

@Service
public class WeiboConnectSynchronizeService implements ISynchronizeService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserService tpUserService;

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		send(authInfo, text + link, image);
	}

	private void send(AuthInfo authInfo, String text, byte[] image) {
		try {
			Timeline timeline = new Timeline(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getAppKey(),
					authInfo.getAppSecret());
			if (image != null) {
				ImageItem item = new ImageItem(image);
				timeline.UploadStatus(text, item);
			} else {
				timeline.UpdateStatus(text);
			}
		} catch (Exception e) {
			log.error("weibo connect sendMessage is error. " + e.getMessage(),
					e);
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {
		send(authInfo, text, image);
	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {
		if (ArrayUtils.isEmpty(fuids)) {
			return;
		}

		for (String fuid : fuids) {
			try {
				Comments comment = new Comments(authInfo.getToken());
				Timeline timeline = new Timeline(authInfo.getToken());
				List<Status> status = timeline.getUserTimeline(fuid, "", 1,
						null, 0, 1);
				if (CollectionUtils.isNotEmpty(status)) {
					String id = status.get(0).getId();
					comment.createComment(text, id);
				}
			} catch (Exception e) {
				log.error("connect weibo sendSysMessage is error."
						+ e.getMessage() + "fuid:" + fuid);
			}
		}

	}

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		try {
			Timeline timeline = new Timeline(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getAppKey(),
					authInfo.getAppSecret());
			TpUser fUser = tpUserService.getTpUserByUid(fuid);
			List<Status> status = timeline.getUserTimeline(
					fUser.getTpIdentity(), "", size, null, 0, 1);
			if (CollectionUtils.isNotEmpty(status)) {
				for (Status s : status) {
					UserStatus userStatus = new UserStatus();
					userStatus.setContent(s.getText());
					userStatus.setTime(s.getCreatedAt());
					userStatusList.add(userStatus);
				}
			}
		} catch (WeiboException e) {
			log.error("weibo connect listStatus is error. " + e.getMessage(), e);
		}
		return userStatusList;
	}

}
