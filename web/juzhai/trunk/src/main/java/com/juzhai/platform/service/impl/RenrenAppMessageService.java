package com.juzhai.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IMessageService;
import com.juzhai.platform.utils.AppPlatformUtils;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;

@Service
public class RenrenAppMessageService implements IMessageService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {
		RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
				authInfo.getAppSecret(), authInfo.getSessionKey());
		int count = client.getNotificationsService().send(
				StringUtils.join(fuids, ","), text + "" + word);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	private RenrenApiClient newRenrenApiClient(String key, String secret,
			String sessionKey) {
		if (RenrenApiConfig.renrenApiKey == null
				|| RenrenApiConfig.renrenApiSecret == null) {
			RenrenApiConfig.renrenApiKey = key;
			RenrenApiConfig.renrenApiSecret = secret;
		}
		RenrenApiClient renrenApiClient = new RenrenApiClient(sessionKey);
		return renrenApiClient;
	}

	@Override
	public boolean sendMessage(String fuids, String content, AuthInfo authInfo) {
		try {
			if (StringUtils.isEmpty(fuids)) {
				return false;
			}
			//人人不支持发送多个循环发送
			for (String fuid : fuids.split(",")) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("method", "message.send");
				paramMap.put("uid", fuid);
				paramMap.put("title", "");
				paramMap.put("content", content);
				String query = AppPlatformUtils.buildQuery(paramMap,
						authInfo.getAppKey(), authInfo.getAppSecret(),
						authInfo.getSessionKey(), "1.0");
				AppPlatformUtils.doPost(
						"http://api.renren.com/restserver.do", query);
			}
		} catch (Exception e) {
			log.error("send renren sysmessage is error fuids:" + fuids
					+ " [error: " + e.getMessage() + "].");
			return false;
		}
		return true;
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo) {
		// TODO Auto-generated method stub
		return false;
	}

}
