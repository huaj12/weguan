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
import com.qplus.QOpenResult;
import com.qplus.QOpenService;

@Service
public class QqPlusSynchronizeService implements ISynchronizeService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public List<UserStatus> listStatus(AuthInfo authInfo, long fuid, int size) {
		return Collections.emptyList();
	}

	@Override
	public void sendMessage(AuthInfo authInfo, String title, String text,
			String link, byte[] image, String imageUrl) {
		try {
			QOpenService service = QOpenService.createInstance(
					Integer.parseInt(authInfo.getAppKey()),
					authInfo.getAppSecret());
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", authInfo.getAppKey());
			map.put("openid", authInfo.getTpIdentity());
			map.put("openkey", authInfo.getToken());
			map.put("title", title);
			map.put("source", "3");
			// if (StringUtils.isNotEmpty(text)) {
			// map.put("summary", text);
			// }
			if (StringUtils.isNotEmpty(imageUrl)) {
				map.put("images", imageUrl);
			}
			QOpenResult result = service.feed(map);
			if (result == null || 0 != result.getIntValue("ret")) {
				log.error("q+ feed is error openid=" + authInfo.getTpIdentity()
						+ "result:" + result);
			}
		} catch (Exception e) {
			log.error("Qplus  sendMessage is error.", e);
		}
	}

	@Override
	public void inviteMessage(AuthInfo authInfo, String text, byte[] image,
			List<String> fuids) {

	}

	@Override
	public void notifyMessage(AuthInfo authInfo, String[] fuids, String text) {

	}

}
