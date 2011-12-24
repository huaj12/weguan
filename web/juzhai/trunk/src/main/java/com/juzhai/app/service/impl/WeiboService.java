package com.juzhai.app.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.WeiboException;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IActService;
import com.juzhai.app.service.IWeiboService;
import com.juzhai.cms.bean.SizeType;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class WeiboService implements IWeiboService {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${weibo.account}")
	private String weiboUid = "";
	private int weiboMaxLength = 280;
	@Autowired
	private IActService actService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IActImageService actImageService;

	@Override
	public void follow(long tpId, long uid) {
		try {
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
			Friendships friendships = new Friendships(authInfo.getToken());
			if (!weiboUid.equals(authInfo.getTpIdentity())) {
				friendships.createFriendshipsById(weiboUid);
			}
		} catch (Exception e) {
			log.error("weibo follow is error." + e.getMessage());
		}

	}

	@Override
	public boolean sendWeiboRequest(long tpId, long uid, String content) {
		try {
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
			if (StringUtils.isEmpty(content)) {
				return false;
			}
			if (authInfo == null) {
				return false;
			}
			Thirdparty tp = InitData.TP_MAP.get(tpId);
			if (tp == null) {
				return false;
			}
			String appLink = tp.getAppUrl() + "?goUri=/app/" + uid;
			content = subContent(content, appLink);
			Timeline timeline = new Timeline(authInfo.getToken());
			timeline.UpdateStatus(content);
			return true;
		} catch (Exception e) {
			log.error("weibo sendWeiboRequest is error." + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean sendFeed(long tpId, long uid, String content, long actId) {
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (StringUtils.isEmpty(content)) {
			return false;
		}
		if (authInfo == null) {
			return false;
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (tp == null) {
			return false;
		}
		Act act = actService.getActById(actId);
		if (act == null) {
			log.error("send Feed act is null");
			return false;
		}
		try {
			String link = tp.getAppUrl();
			String appLink = link + "?goUri=/app/showAct/" + actId;
			content = subContent(content, appLink);
			Timeline timeline = new Timeline(authInfo.getToken());
			sendWeibo(actId, timeline, content);
		} catch (Exception e) {
			log.error("weibo sendFeed is error." + e.getMessage());
			return false;
		}
		return true;
	}

	private void sendWeibo(long actId, Timeline timeline, String content)
			throws WeiboException {
		Act act = actService.getActById(actId);
		if (act != null) {
			// TODO (review) 这里代码我有改动过，检查一下是否有问题
			byte[] imgContent = actImageService.getActFile(act.getId(),
					act.getLogo(), SizeType.BIG);
			if (imgContent == null) {
				timeline.UpdateStatus(content);
			} else {
				ImageItem item = new ImageItem(imgContent);
				timeline.UploadStatus(content, item);
			}
		} else {
			timeline.UpdateStatus(content);
		}
	}

	private String subContent(String text, String appLink) {
		int count = weiboMaxLength - StringUtil.chineseLength(appLink);
		text = TextTruncateUtil.truncate(text, count - 5, "...") + appLink;
		return text;
	}

}
