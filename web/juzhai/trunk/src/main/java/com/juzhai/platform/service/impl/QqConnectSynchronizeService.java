package com.juzhai.platform.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.bean.UserStatus;
import com.juzhai.platform.service.ISynchronizeService;
import com.qq.connect.ShareToken;

@Service
public class QqConnectSynchronizeService implements ISynchronizeService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		ShareToken share = new ShareToken(authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", title);
			map.put("url", link);
			if (StringUtils.isNotEmpty(text)) {
				map.put("summary", text);
			}
			if (StringUtils.isNotEmpty(imageUrl)) {
				map.put("images", imageUrl);
			}
			Map<String, String> code = share.addShare(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getTpIdentity(), map);
			if (!"ok".equals(code.get("msg"))) {
				log.error("QQ content sendMessage is error. msg="
						+ code.get("msg") + "| ret=" + code.get("ret"));
			}
		} catch (Exception e) {
			log.error("QQ content sendMessage is error." + e.getMessage());
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {
	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {

	}

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {

		return Collections.emptyList();
	}

}
