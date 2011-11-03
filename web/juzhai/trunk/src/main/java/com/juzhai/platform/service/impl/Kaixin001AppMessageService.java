package com.juzhai.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IMessageService;
import com.juzhai.platform.utils.AppPlatformUtils;

@Service
public class Kaixin001AppMessageService implements IMessageService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext, String link,
			String word, String text, String picurl, AuthInfo authInfo) {
		boolean flag = false;
		try {
			if(authInfo==null){
				log.error("send  kaixin sysmessage authInfo is null ");
				return false;
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("fuids", StringUtils.join(fuids, ","));
			paramMap.put("linktext", linktext);
			paramMap.put("link", link);
			if (!StringUtils.isEmpty(word)) {
				paramMap.put("word", word);
			}
			if (!StringUtils.isEmpty(picurl)) {
				paramMap.put("picurl", picurl);
			}
			paramMap.put("text", text);
			String query = AppPlatformUtils.sessionKeyBuildQuery(paramMap,
					authInfo.getSessionKey());
			AppPlatformUtils.doPost(
					"https://api.kaixin001.com/sysnews/send.json", query);
			return true;
		} catch (Exception e) {
			log.error("send kaixin sysmessage is error fuids:" + fuids
					+ " [error: " + e.getMessage() + "].");
			return false;
		}
	}

	@Override
	public boolean sendMessage(String fuids, String content, AuthInfo authInfo) {
		boolean flag = false;
		try {
			if(authInfo==null){
				log.error("send  kaixin message authInfo is null ");
				return false;
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("fuids", fuids);
			paramMap.put("content", content);
			String query = AppPlatformUtils.sessionKeyBuildQuery(paramMap,
					authInfo.getSessionKey());
			String ret = AppPlatformUtils.doPost(
					"https://api.kaixin001.com/message/send.json", query);
			JSONObject jObject = JSONObject.fromObject(ret);
			if (!StringUtils.isEmpty(jObject.getString("mid"))) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			log.error("send  kaixin message is error fuids:" + fuids + " [error: " + e.getMessage() + "].");
			return flag;
		}
	}

	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo) {
		boolean flag = false;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			if(authInfo==null){
				log.error("send  kaixin feed authInfo is null ");
				return false;
			}
			paramMap.put("linktext", linktext);
			paramMap.put("link", link);
			if(!StringUtils.isEmpty(word)){
				paramMap.put("word", word);
			}
			if(!StringUtils.isEmpty(picurl)){
				paramMap.put("picurl", picurl);
			}
			paramMap.put("text", text);
			String query = AppPlatformUtils.sessionKeyBuildQuery(paramMap,
					authInfo.getSessionKey());
			String ret = AppPlatformUtils.doPost(
					"https://api.kaixin001.com/feed/send.json", query);
			JSONObject jObject = JSONObject.fromObject(ret);
			if (!StringUtils.isEmpty(jObject.getString("result"))) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			log.error("send  kaixin feed is error "+ " [error: " + e.getMessage() + "].");
			return flag;
		}
	}

}
