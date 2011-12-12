package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.bean.AccessToken;

@Service
public class RenrenConnectUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(RenrenConnectUserService.class);
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		List<TpFriend> friendIdList = new ArrayList<TpFriend>();
		RenrenApiClient client = newRenrenApiClient(authInfo);
		int start = 1;
		int num = 500;
		while (true) {
			JSONArray array = client.getFriendsService().getFriends(start, num);
			List<String> fuids = new ArrayList<String>(array.size());
			for (Object o : array) {
				JSONObject object = (JSONObject) o;
				fuids.add(String.valueOf(object.get("id")));
			}
			JSONArray fArray = client.getUserService().getInfo(
					StringUtils.join(fuids, ","),
					"uid,name,sex,birthday,mainurl");
			for (Object fInfo : fArray) {
				TpFriend tpFriend = new TpFriend();
				JSONObject info = (JSONObject) fInfo;
				String uid = String.valueOf(info.get("uid"));
				JSONObject cityObj = client.getUserService().getProfileInfo(
						String.valueOf(uid), "network_name");
				tpFriend.setUserId(uid);
				tpFriend.setName((String) info.get("name"));
				tpFriend.setGender(((Long) info.get("sex")).intValue());
				tpFriend.setLogoUrl((String) info.get("mainurl"));
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
				String cityName = (String) cityObj.get("network_name");
				tpFriend.setCity(cityName);
				friendIdList.add(tpFriend);
			}
			if (array.size() < 500) {
				break;
			}
			start++;
		}
		return friendIdList;
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		RenrenApiClient client = newRenrenApiClient(authInfo);
		JSONArray array = client.getFriendsService().getAppFriends("uid");
		List<String> friendIdList = new ArrayList<String>(array.size());
		for (Object o : array) {
			JSONObject object = (JSONObject) o;
			friendIdList.add(String.valueOf(object.get("uid")));
		}
		return friendIdList;
	}

	@Override
	public Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		RenrenApiClient client = newRenrenApiClient(authInfo);
		int uid = client.getUserService().getLoggedInUser();
		String[] fields = new String[] { "name", "sex", "hometown_location",
				"mainurl", "birthday" };
		JSONArray array = client.getUserService().getInfo(String.valueOf(uid),
				StringUtils.join(fields, ","));
		JSONObject cityObj = client.getUserService().getProfileInfo(
				String.valueOf(uid), "network_name");
		JSONObject object = (JSONObject) array.get(0);
		JSONObject hometown = (JSONObject) object.get("hometown_location");
		String name = (String) object.get("name");
		Long sex = (Long) object.get("sex");
		String mainurl = (String) object.get("mainurl");
		String home = (String) hometown.get("province");
		Profile profile = new Profile();
		profile.setNickname(TextTruncateUtil.truncate(
				HtmlUtils.htmlUnescape(name), nicknameLengthMax,
				StringUtils.EMPTY));
		profile.setGender(sex.intValue());
		profile.setLogoPic(mainurl);
		profile.setHome(home);
		String birthday = String.valueOf(object.get("birthday"));
		if (StringUtils.isNotEmpty(birthday)) {
			try {
				String[] birthdays = birthday.split("[^0-9]");
				int birthYear = Integer.valueOf(birthdays[0]);
				int brithMonth = Integer.valueOf(birthdays[1]);
				int brithDay = Integer.valueOf(birthdays[2]);
				if (birthYear > 1900) {
					profile.setBirthYear(birthYear);
				}
				if (brithMonth > 0 && brithMonth < 13) {
					profile.setBirthMonth(brithMonth);
				}
				if (brithDay > 0 && brithDay < 32) {
					profile.setBirthDay(brithDay);
				}
			} catch (Exception e) {
				log.error("parse birthday error.", e);
			}
		}
		String cityName = (String) cityObj.get("network_name");
		City city = InitData.getCityByName(cityName);
		if (null != city) {
			profile.setCity(city.getId());
			profile.setProvince(city.getProvinceId());
		}
		return profile;
	}

	@Override
	public String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		if (tp == null) {
			return null;
		}
		AccessToken accessToken = RenrenApiClient.getOAuthAccessTokenFromCode(
				code, tp.getAppKey(), tp.getAppSecret(), tp.getAppUrl());
		if (accessToken == null) {
			return null;
		}
		return accessToken.getAccessToken();
	}

	@Override
	protected String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		String code=request.getParameter("code");
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		if (null == tp) {
			return null;
		}
		String accessToken = getOAuthAccessTokenFromCode(tp, code);
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}
		RenrenApiClient client = new RenrenApiClient(accessToken, true);
		client.renrenApiOauth(tp.getAppKey(), tp.getAppSecret());
		int uid = client.getUserService().getLoggedInUser();
		if (uid == 0) {
			return null;
		}
		// 构建authInfo
		authInfo.setThirdparty(tp);
		authInfo.setToken(accessToken);
		authInfo.setTpIdentity(String.valueOf(uid));
		return String.valueOf(uid);
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		return true;
	}

	private RenrenApiClient newRenrenApiClient(AuthInfo authInfo) {
		RenrenApiClient renrenApiClient = new RenrenApiClient(
				authInfo.getToken(), true);
		renrenApiClient.renrenApiOauth(authInfo.getAppKey(),
				authInfo.getAppSecret());
		return renrenApiClient;
	}

}
