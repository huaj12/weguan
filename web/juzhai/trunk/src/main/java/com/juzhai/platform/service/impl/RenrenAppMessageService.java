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
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IProfileService;
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
	@Autowired
	private IProfileService profileService;

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {
		RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
				authInfo.getAppSecret(), authInfo.getSessionKey());
		int count = client.getNotificationsService().send(
				StringUtils.join(fuids, ","), word + "" + text);
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
	public boolean sendMessage(long sendId, String fuid, String fname,String content,
			AuthInfo authInfo, long actId, String link,String typeWeibo,String typeComment) {
		try {
			if (content.trim().length() >30) {
				log.error("about friends  content is too long ");
				return false;
			}
			if (StringUtils.isEmpty(fuid)) {
				return false;
			}
			ProfileCache pro = profileService.getProfileCacheByUid(sendId);
			if (null == pro) {
				return false;
			}
			String text = "";
			if (actId > 0) {
				text = messageSource.getMessage(
						TpMessageKey.RENREN_SEND_MESSAGE,
						new Object[] { link + "?goUri=/app/" + sendId,
								pro.getNickname(), link, content,
								link + "?goUri=/app/showAct/" + actId },
						Locale.SIMPLIFIED_CHINESE);
			} else {
				text = messageSource.getMessage(
						TpMessageKey.RENREN_SEND_MESSAGE,
						new Object[] { link + "?goUri=/app/" + sendId,
								pro.getNickname(), link, content,
								link + "?goUri=/app/" + sendId },
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
					+ e.getMessage() + "]. fuid:"+fuid);
			return false;
		}
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo, String name,long actId) {
		try {
			RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
					authInfo.getAppSecret(), authInfo.getSessionKey());
			String ret=client.getNotificationsService().sendFeed(linktext, link, word, text, picurl, name);
			if(StringUtils.isEmpty(ret)){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("send renren sendFeed is error " + " [error: "
					+ e.getMessage() + "]. fuid:"+authInfo.getTpIdentity());
			return false;
		}
	}

	@Override
	public boolean sendQuestionMessage(AuthInfo authInfo, List<String> fuids,
			long sendId, String linktext, String link, String word, String text) {
		text = "";
		ProfileCache pro = profileService.getProfileCacheByUid(sendId);
		if (pro == null) {
			return false;
		}
		String content = getContent(
				TpMessageKey.RENREN_QUERTION_MESSAGE,
				new Object[] { link + "?goUri=/app/" + sendId,
						pro.getNickname(), word, link + "?goUri=/app/judge" });
		return sendSysMessage(fuids, linktext, link, content, text,
				StringUtils.EMPTY, authInfo);
	}

	private String getContent(String code, Object[] args) {
		return messageSource.getMessage(code, args, StringUtils.EMPTY,
				Locale.SIMPLIFIED_CHINESE);
	}

	@Override
	public boolean sendMatchMessage(long sendId, List<String> fuids,
			String linktext, String link, String word, String text,
			String picurl, AuthInfo authInfo, long actId) {
		ProfileCache pro = profileService.getProfileCacheByUid(sendId);
		if (pro == null) {
			return false;
		}
		word = "";
		String content = getContent(
				TpMessageKey.RENREN_MATCH_MESSAGE,
				new Object[] { authInfo, link + "?goUri=/app/" + sendId,
						pro.getNickname(), text, link });
		return sendSysMessage(fuids, linktext, link, word, content,
				StringUtils.EMPTY, authInfo);
	}

}
