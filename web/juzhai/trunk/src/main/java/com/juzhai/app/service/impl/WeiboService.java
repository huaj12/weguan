package com.juzhai.app.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weibo4j.Friendships;
import weibo4j.Timeline;

import com.juzhai.app.service.IWeiboService;
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
	private ITpUserAuthService tpUserAuthService;

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
			if(StringUtils.isEmpty(content)){
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
			int count = weiboMaxLength - StringUtil.chineseLength(appLink);
			content = TextTruncateUtil.truncate(content, count - 5, "...")
					+ appLink;
			Timeline timeline = new Timeline(authInfo.getToken());
			timeline.UpdateStatus(content);
			return true;
		} catch (Exception e) {
			log.error("weibo sendWeiboRequest is error." + e.getMessage());
			return false;
		}
	}

}
