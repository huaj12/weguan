package com.juzhai.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.core.util.TextTruncateUtil;
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
	public boolean sendMessage(String fuid, String content, AuthInfo authInfo) {
		try {
			if (StringUtils.isEmpty(fuid)) {
				return false;
			}

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("method", "message.send");
			paramMap.put("uid", fuid);
			paramMap.put("title", TextTruncateUtil.truncate(content,15,""));
			paramMap.put("content", content);
			String query = AppPlatformUtils.buildQuery(paramMap,
					authInfo.getAppKey(), authInfo.getAppSecret(),
					authInfo.getSessionKey(), "1.0");
			String ret=AppPlatformUtils.doPost("http://api.renren.com/restserver.do",
					query);
			JSONObject jObject = JSONObject.fromObject(ret);
			if (StringUtils.isNotEmpty(jObject.getString("result"))) {
				return  true;
			} else {
				log.error("send renren Message is error. reg:"+ret);
				return false;
			}
		} catch (Exception e) {
			log.error("send renren sysmessage is error fuid:" + fuid
					+ " [error: " + e.getMessage() + "].");
			return false;
		}
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo) {
		try{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("method", "feed.publishFeed");
		paramMap.put("name", "拒宅器");
		paramMap.put("description", word);
		paramMap.put("url", link);
		paramMap.put("image", picurl);
		paramMap.put("caption", "");
		paramMap.put("action_name ", linktext);
		paramMap.put("action_link", link);
		paramMap.put("message", text);
		paramMap.put("format", "JSON");
		String query = AppPlatformUtils.buildQuery(paramMap,
				authInfo.getAppKey(), authInfo.getAppSecret(),
				authInfo.getSessionKey(), "1.0");
		String ret=AppPlatformUtils.doPost("http://api.renren.com/restserver.do",
				query);
		JSONObject jObject = JSONObject.fromObject(ret);
		if (StringUtils.isNotEmpty(jObject.getString("post_id"))) {
			return  true;
		} else {
			log.error("send renren sendFeed is error. reg:"+ret);
			return false;
		}
		}catch (Exception e) {
			log.error("send renren sendFeed is error "
					+ " [error: " + e.getMessage() + "].");
			return false;
		}
	}

}
