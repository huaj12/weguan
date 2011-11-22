package com.juzhai.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.service.IMessageService;
import com.juzhai.platform.utils.AppPlatformUtils;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;

@Service
public class RenrenAppMessageService implements IMessageService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private MessageSource messageSource;

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
	public boolean sendMessage(long sendId, String fuid, String content,
			AuthInfo authInfo, long actId, String link) {
		try {
			if (StringUtils.isEmpty(fuid)) {
				return false;
			}
			TpUser tpUser = tpUserService.getTpUserByUid(sendId);
			if (null == tpUser) {
				return false;
			}
			String text = "";
			if (actId > 0) {
				text = messageSource.getMessage(
						TpMessageKey.RENREN_SEND_MESSAGE,
						new Object[] { tpUser.getTpIdentity(), link, content,
								link + "?goUri=/app/showAct/" + actId },
						Locale.SIMPLIFIED_CHINESE);
			} else {
				text = messageSource.getMessage(
						TpMessageKey.RENREN_SEND_MESSAGE,
						new Object[] { tpUser.getTpIdentity(), link, content,
								link + "?goUri=/app/" + fuid },
						Locale.SIMPLIFIED_CHINESE);
			}
			RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
					authInfo.getAppSecret(), authInfo.getSessionKey());
			int count = client.getNotificationsService().send(fuid, text);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("send renren sendMessage is error " + " [error: "
					+ e.getMessage() + "].");
			return false;
		}
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo,String name) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("method", "feed.publishFeed");
			paramMap.put("name", name);
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
			String ret = AppPlatformUtils.doPost(
					"http://api.renren.com/restserver.do", query);
			JSONObject jObject = JSONObject.fromObject(ret);
			if (StringUtils.isNotEmpty(jObject.getString("post_id"))) {
				return true;
			} else {
				log.error("send renren sendFeed is error. reg:" + ret);
				return false;
			}
		} catch (Exception e) {
			log.error("send renren sendFeed is error " + " [error: "
					+ e.getMessage() + "].");
			return false;
		}
	}

}
