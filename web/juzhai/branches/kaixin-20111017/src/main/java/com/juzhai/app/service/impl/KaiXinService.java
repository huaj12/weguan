package com.juzhai.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.juzhai.app.model.AppUser;
import com.juzhai.app.service.IAppService;
import com.juzhai.app.util.AppPlatformUtils;
import com.juzhai.app.util.ConvertObject;
import com.juzhai.core.exception.JuzhaiAppException;
import com.juzhai.passport.bean.AuthInfo;

@Service
public class KaiXinService implements IAppService {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public String[] appFriends(AuthInfo authInfo, int start, int num)
			throws JuzhaiAppException {
		if (start < 0) {
			start = 0;
		}

		if (num < 0 || num > 50) {
			num = 20;
		}
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("start", String.valueOf(start));
			paramMap.put("num", String.valueOf(num));
			String query = AppPlatformUtils.sessionKeyBuildQuery(paramMap,
					authInfo.getSessionKey());
			String ret = AppPlatformUtils.doSllGet(
					"https://api.kaixin001.com/app/friends.json", query);
			JSONObject jObject = JSONObject.fromObject(ret);
			JSONArray list = (JSONArray) jObject.get("uids");
			String[] uids = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				uids[0] = list.getString(i);
			}
			return uids;
		} catch (Exception e) {
			throw new JuzhaiAppException("get kaixin appFriends is error", e);
		}

	}

	@Override
	public List<AppUser> getFriends(AuthInfo authInfo, String fields,
			int start, int num) throws JuzhaiAppException {
		if (start < 0) {
			start = 0;
		}

		if (num < 0 || num > 50) {
			num = 20;
		}
		String ret="";
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("fields", fields);
			paramMap.put("start", String.valueOf(start));
			paramMap.put("num", String.valueOf(num));
			String query = AppPlatformUtils.sessionKeyBuildQuery(paramMap,
					authInfo.getSessionKey());
			 ret = AppPlatformUtils.doSllGet(
					"https://api.kaixin001.com/friends/me.json", query);
			JSONObject jObject = JSONObject.fromObject(ret);
			return ConvertObject.constructUser(jObject);
		} catch (Exception e) {
			throw new JuzhaiAppException("get kaixin getFriends is error ret="+ret, e);
		}
	}

	@Override
	public boolean sendSysMessage(String fuids, String linktext, String link,
			String word, String text, String picurl, AuthInfo authInfo) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("fuids", fuids);
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
			AppPlatformUtils.doMsgPost(
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
			log.error("send  kaixin message is error fuids:" + fuids, e);
			return flag;
		}
	}

	@Override
	public int getAllocation(AuthInfo authInfo) {
		String ret="";
		try{
		Map<String, String> paramMap = new HashMap<String, String>();
		String query = AppPlatformUtils.sessionKeyBuildQuery(paramMap,
				authInfo.getSessionKey());
	    ret=AppPlatformUtils.doSllGet("https://api.kaixin001.com/app/rate_limit_status.json", query);
		JSONObject jObject = JSONObject.fromObject(ret);
			return jObject.getInt("remaining_hits");
		}catch (Exception e) {
			log.error("getAllocation is error ret:" + ret, e);
			return 0;
		}
		
	}
	
	public static void main(String[] args){
		AuthInfo a=new AuthInfo();
		a.setSessionKey("2328283_100012402_2328283_1318852016_ea8b5ebbb46547e0f2bcc504b027b801");
		System.out.println(new KaiXinService().getAllocation(a));
	}

}
