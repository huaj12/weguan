package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import weibo4j.Friendships;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.Weibo;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.renren.api.client.RenrenApiClient;

@Service
public class WeiboAppUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(RenrenAppUserService.class);

	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	private String newWeibo(AuthInfo authInfo) {
		Weibo weibo = new Weibo();
		weibo.setToken(authInfo.getToken());
		String uid = authInfo.getTpIdentity();
		return uid;
	}

	@Override
	public List<TpFriend> getAllFriends(AuthInfo authInfo) {
		List<TpFriend> friendIdList = new ArrayList<TpFriend>();
		String uid = newWeibo(authInfo);
		Friendships fm = new Friendships();
		try {
			List<User> users = fm.getFriendsBilateral(uid);
			for (User user : users) {
				TpFriend tpFriend = new TpFriend();
				tpFriend.setUserId(user.getId());
				tpFriend.setName(user.getName());
				// 值1表示男性；值0表示女性
				String gender = user.getGender();
				int sex = 0;
				if ("m".equals(gender)) {
					sex = 1;
				}
				tpFriend.setGender(sex);
				tpFriend.setLogoUrl(user.getProfileImageURL().toString());
				// 微博获取不到生日需要申请高级接口
				tpFriend.setCity(user.getLocation());
				friendIdList.add(tpFriend);
			}
		} catch (WeiboException e) {
			log.error("weibo  getAllFriends is erorr." + e.getMessage());
			return null;
		}
		return friendIdList;
	}

	@Override
	public List<String> getAppFriends(AuthInfo authInfo) {
		String uid = newWeibo(authInfo);
		Friendships fm = new Friendships();
		List<String> fuids = new ArrayList<String>();
		try {
			String[] ids = fm.getFriendsBilateralIds(uid);
			for (String id : ids) {
				if (isInstalled(authInfo.getThirdpartyName(), id)) {
					fuids.add(id);
				}
			}
		} catch (WeiboException e) {
			log.error("weibo  getAppFriends is erorr." + e.getMessage());
			return null;
		}
		return fuids;
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		try {
			String uid = newWeibo(authInfo);
			Users users = new Users();
			User user = users.showUserById(uid);
			Profile profile = new Profile();
			profile.setNickname(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(user.getName()), nicknameLengthMax,
					StringUtils.EMPTY));
			int sex = 0;
			if ("m".equals(user.getGender())) {
				sex = 1;
			}
			profile.setGender(sex);
			profile.setLogoPic(user.getProfileImageURL().toString());
			// 没有家乡用所在地代替
			profile.setHome(user.getLocation());
			// 获取不到生日需要高级接口
			String cityName = user.getLocation();
			City city = InitData.getCityByName(cityName);
			if (null != city) {
				profile.setCity(city.getId());
				profile.setProvince(city.getProvinceId());
			}
			return profile;
		} catch (Exception e) {
			log.error("weibo  convertToProfile is erorr." + e.getMessage());
			return null;
		}

	}

	@Override
	protected String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		Oauth oauth = newOauth(tp, request);
		if (oauth == null) {
			return null;
		}
		authInfo.setThirdparty(tp);
		authInfo.setTpIdentity(oauth.user_id);
		authInfo.setToken(oauth.getToken());
		return oauth.user_id;
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		Oauth oauth = newOauth(tp, request);
		if (oauth != null) {
			return true;
		} else {
			return false;
		}

	}

	private Oauth newOauth(Thirdparty tp, HttpServletRequest request) {
		Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
				tp.getAppUrl());
		String signedRequest = request.getParameter("signed_request");
		try {
			if (StringUtils.isEmpty(oauth.parseSignedRequest(signedRequest))) {
				return null;
			}
			;
		} catch (Exception e) {
			return null;
		}
		return oauth;
	}

}
