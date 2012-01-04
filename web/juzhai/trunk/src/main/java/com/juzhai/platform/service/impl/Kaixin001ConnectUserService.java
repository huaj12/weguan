package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kx2_4j.KxException;
import kx2_4j.KxSDK;
import kx2_4j.UIDs;
import kx2_4j.User;
import kx2_4j.http.AccessToken;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@Service
public class Kaixin001ConnectUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(Kaixin001ConnectUserService.class);
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		KxSDK kxSDK = newKxSDK2(authInfo);
		List<TpFriend> friendIdList = new ArrayList<TpFriend>();
		try {
			int start = 0;
			int num = 50;
			String fields = "uid,name,gender,city,birthday,logo120";
			while (true) {
				List<User> userList = kxSDK.getFriends(fields, start, num);
				if (CollectionUtils.isEmpty(userList)) {
					break;
				}
				for (User user : userList) {
					TpFriend tpFriend = kxUserTpFriend(user);
					if (null != tpFriend) {
						friendIdList.add(tpFriend);
					}
				}
				if (userList.size() < num) {
					break;
				}
				start += num;
			}
		} catch (KxException e) {
			log.error(e.getMessage());
		}
		return friendIdList;
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		KxSDK kxSDK = newKxSDK2(authInfo);
		List<String> friendIdList = new ArrayList<String>();
		try {
			int start = 0;
			int num = 50;
			while (true) {
				UIDs uids = kxSDK.getAppFriendUids(start, num);
				if (null == uids || ArrayUtils.isEmpty(uids.getIDs())) {
					break;
				}
				for (long userId : uids.getIDs()) {
					friendIdList.add(String.valueOf(userId));
				}
				if (uids.getNextCursor() <= start) {
					break;
				} else {
					start = Long.valueOf(uids.getNextCursor()).intValue();
				}
			}
		} catch (KxException e) {
			log.error(e.getMessage());
		}
		return friendIdList;
	}

	private TpFriend kxUserTpFriend(User user) {
		if (null == user) {
			return null;
		}
		TpFriend tpFriend = new TpFriend();
		tpFriend.setUserId(String.valueOf(user.getUid()));
		tpFriend.setName(user.getName());
		tpFriend.setGender(Math.abs(user.getGender() - 1));
		tpFriend.setCity(user.getCity());

		if (StringUtils.isNotEmpty(user.getBirthday())) {
			try {
				String[] birthday = user.getBirthday().split("[^0-9]");
				if (StringUtils.isNotEmpty(birthday[0])) {
					int birthYear = Integer.valueOf(birthday[0]);
					if (birthYear > 1900) {
						tpFriend.setBirthYear(birthYear);
					}
				}
			} catch (Exception e) {
				log.error("parse birthday error.", e);
			}
		}
		tpFriend.setLogoUrl(user.getLogo120());
		return tpFriend;
	}

	@Override
	public String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		String token = null;
		KxSDK kxsdk = new KxSDK();
		kxsdk.CONSUMER_KEY = tp.getAppKey();
		kxsdk.CONSUMER_SECRET = tp.getAppSecret();
		kxsdk.Redirect_uri = tp.getAppUrl();
		try {
			kx2_4j.http.AccessToken kxToken = kxsdk
					.getOAuthAccessTokenFromCode(code);
			token = kxToken.getToken();
		} catch (KxException e) {
			log.error("kaixin getOAuthAccessTokenFromCode is error.  error message:"
					+ e.getMessage());
			return null;
		}
		return token;
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		KxSDK kxSDK = newKxSDK2(authInfo);
		String[] fields = new String[] { "name", "gender", "hometown", "city",
				"logo120", "bodyform", "interest", "school", "company",
				"birthday" };
		User user = null;
		try {
			user = kxSDK.getMyInfo(StringUtils.join(fields, ","));
		} catch (KxException e) {
			log.error(e.getMessage(), e);
		}
		return kxUserToProfile(user);
	}

	private Profile kxUserToProfile(User user) {
		if (null == user) {
			return null;
		}
		Profile profile = new Profile();
		profile.setNickname(TextTruncateUtil.truncate(
				HtmlUtils.htmlUnescape(user.getName()), nicknameLengthMax,
				StringUtils.EMPTY));
		profile.setGender(Math.abs(user.getGender() - 1));
		profile.setHome(HtmlUtils.htmlEscape(user.getHometown()));
		profile.setLogoPic(user.getLogo120());
		profile.setShape(HtmlUtils.htmlEscape(user.getBodyform()));
		if (StringUtils.isNotEmpty(user.getBirthday())) {
			try {
				String[] birthdays = user.getBirthday().split("[^0-9]");
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
		String cityName = user.getCity();
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
		KxSDK kxSDK = new KxSDK();
		AccessToken kxToken = new AccessToken(accessToken, "");
		kxSDK.setOAuthAccessToken(kxToken);
		User user = null;
		try {
			user = kxSDK.getMyInfo("uid");
		} catch (KxException e) {
			log.error(e.getMessage(), e);
		}
		if (null == user || user.getUid() <= 0) {
			return null;
		}
		// 构建authInfo
		authInfo.setThirdparty(tp);
		authInfo.setTpIdentity(String.valueOf(user.getUid()));
		authInfo.setToken(accessToken);
		return String.valueOf(user.getUid());
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		return true;

	}

	private KxSDK newKxSDK2(AuthInfo authInfo) {
		KxSDK kxSDK = new KxSDK();
		if (StringUtils.isEmpty(kxSDK.CONSUMER_KEY)
				|| StringUtils.isEmpty(kxSDK.CONSUMER_SECRET)) {
			kxSDK.CONSUMER_KEY = authInfo.getAppKey();
			kxSDK.CONSUMER_SECRET = authInfo.getAppSecret();
		}

		AccessToken kxToken = new AccessToken(authInfo.getToken(), "");
		kxSDK.setOAuthAccessToken(kxToken);
		return kxSDK;
	}

	@Override
	public List<String> getInstallFollows(AuthInfo authInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
