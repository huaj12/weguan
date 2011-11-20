package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import kx4j.KxException;
import kx4j.KxSDK;
import kx4j.User;

import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;

@Service
public class RenrenAppUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(RenrenAppUserService.class);

	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		List<TpFriend> friendIdList = new ArrayList<TpFriend>();
		RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
				authInfo.getAppSecret(), authInfo.getSessionKey());
		while (true) {
			JSONArray array = client.getFriendsService().getFriends(1, 500);
			List<String> fuids = new ArrayList<String>(array.size());
			for (Object o : array) {
				JSONObject object = (JSONObject) o;
				fuids.add(String.valueOf((Long) object.get("id")));
			}
			JSONArray fArray = client.getUserService().getInfo(
					StringUtils.join(fuids, ","),
					"uid,name,sex,birthday,hometown_location,headurl");
			for (Object fInfo : fArray) {
				TpFriend tpFriend = new TpFriend();
				JSONObject info = (JSONObject) fInfo;
				tpFriend.setUserId(String.valueOf((Long) info.get("id")));
				tpFriend.setName((String) info.get("name"));
				tpFriend.setGender(((Long) info.get("sex")).intValue());
				tpFriend.setLogoUrl((String) info.get("headurl"));
				String birthday = (String) info.get("birthday");
				if (StringUtils.isNotEmpty(birthday)) {
					try {
						String[] birthdays = birthday.split("[^0-9]");
						int birthYear = Integer.valueOf(birthdays[0]);
						if (birthYear > 1900) {
							tpFriend.setBirthYear(birthYear);
						}
					} catch (Exception e) {
						log.error("parse birthday error.", e);
					}
				}
				JSONObject hometown = (JSONObject) info
						.get("hometown_location");
				tpFriend.setCity((String) hometown.get("city"));
			}
			if (array.size() < 500) {
				break;
			}
		}
		return friendIdList;
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
				authInfo.getAppSecret(), authInfo.getSessionKey());
		JSONArray array = client.getFriendsService().getAppFriends("uid");
		List<String> friendIdList = new ArrayList<String>(array.size());
		for (Object o : array) {
			JSONObject object = (JSONObject) o;
			friendIdList.add(String.valueOf((Long) object.get("uid")));
		}
		return friendIdList;
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		RenrenApiClient client = newRenrenApiClient(authInfo.getAppKey(),
				authInfo.getAppSecret(), authInfo.getSessionKey());
		int uid = client.getUserService().getLoggedInUser();
		String[] fields = new String[] { "name", "sex", "hometown_location",
				"headurl" };
		JSONArray array = client.getUserService().getInfo(String.valueOf(uid),
				StringUtils.join(fields, ","));
		JSONObject object = (JSONObject) array.get(0);
		JSONObject hometown = (JSONObject) object.get("hometown_location");
		String name = (String) object.get("name");
		Long sex = (Long) object.get("sex");
		String headurl = (String) object.get("headurl");
		String home = (String) hometown.get("province");
		Profile profile = new Profile();
		profile.setNickname(TextTruncateUtil.truncate(
				HtmlUtils.htmlUnescape(name), nicknameLengthMax,
				StringUtils.EMPTY));
		profile.setGender(sex.intValue());
		profile.setLogoPic(headurl);
		profile.setHome(home);
		String cityName = (String) hometown.get("city");
		City city = InitData.getCityByName(cityName);
		if (null != city) {
			profile.setCity(city.getId());
			profile.setProvince(city.getProvinceId());
		}
		return profile;
	}

	@Override
	protected String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		String sessionKey = request.getParameter("xn_sig_session_key");
		if (StringUtils.isEmpty(sessionKey)) {
			return null;
		}
		RenrenApiClient client = newRenrenApiClient(tp.getAppKey(),
				tp.getAppSecret(), sessionKey);
		int uid = client.getUserService().getLoggedInUser();
		if (uid == 0) {
			return null;
		}
		// 构建authInfo
		authInfo.setThirdparty(tp);
		authInfo.setSessionKey(sessionKey);
		return String.valueOf(uid);
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		String queryString = request.getQueryString();
		
		String[] parms = queryString.split("&");
		Map<String, String> map = new HashMap<String, String>();
		String sig = "";
		for (String parm : parms) {
			String[] strs = parm.split("=");
			String key = strs[0];
			String value="";
			try{
				 value=strs[1];
			}catch (Exception e) {
				 value="";
			}
			if(key.indexOf("xn_sig_")!=-1){
				if("xn_sig_ext_perm".equals(key)){
					value=value.replaceAll("%2C",",");	
				}
				map.put(key.replaceAll("xn_sig_",""), value);
			}else{
				if("xn_sig".equals(key)){
					sig = value;
				}
			}
		}
		String request_str = "";
		Object[] paramKeys = map.keySet().toArray();
		Arrays.sort(paramKeys);
		for (int i = 0; i < paramKeys.length; i++) {
			request_str += paramKeys[i] + "=" + map.get(paramKeys[i]);
		}
		String localsig = DigestUtils.md5Hex(request_str + tp.getAppSecret());
		if (localsig.equals(sig)) {
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

}
