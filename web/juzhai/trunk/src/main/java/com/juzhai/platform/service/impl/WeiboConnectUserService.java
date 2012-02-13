package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

import com.juzhai.core.Constants;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.Municipal;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.Town;

@Service
public class WeiboConnectUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(WeiboConnectUserService.class);

	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	@Override
	public String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
				tp.getAppUrl());
		AccessToken accessToken = null;
		try {
			accessToken = oauth.getAccessTokenByCode(code);
		} catch (WeiboException e) {
			log.error("weibo getOAuthAccessTokenFromCode is error"
					+ e.getMessage());
		}
		if (null == accessToken) {
			return null;
		}
		return accessToken.getAccessToken();
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		try {
			Users users = new Users(authInfo.getToken());
			User user = users.showUserById(authInfo.getTpIdentity());
			Profile profile = new Profile();
			profile.setNickname(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(user.getName()), nicknameLengthMax,
					StringUtils.EMPTY));
			int sex = 0;
			if ("m".equals(user.getGender())) {
				sex = 1;
			}
			profile.setGender(sex);
			// //获取用户个人主页
			String blog = user.getUserDomain();
			if (StringUtils.isEmpty(blog)) {
				blog = user.getId();
			}
			profile.setBlog("www.weibo.com/" + blog);
			profile.setLogoPic(user.getAvatarLarge());
			// 用户简介
			profile.setFeature(user.getDescription());
			// 没有家乡用所在地代替
			// profile.setHome(user.getLocation());
			// 获取不到生日需要高级接口
			String cityName = user.getLocation();
			City city = null;
			Town town = null;
			String[] str = cityName.split(" ");
			if (ArrayUtils.isNotEmpty(str)) {
				// 直辖市
				if (Municipal.getMunicipalEnum(str[0]) != null) {
					city = InitData.getCityByName(str[0]);
					if (city != null) {
						town = InitData.getTownByNameAndCityId(city.getId(),
								str[str.length - 1]);
					}
				} else {
					// 非直辖市
					city = InitData.getCityByName(str[str.length - 1]);
				}

			}
			if (null != city) {
				profile.setCity(city.getId());
				profile.setProvince(city.getProvinceId());
				if (null != town) {
					profile.setTown(town.getId());
				}
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
		String code = request.getParameter("code");
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
		String uid = "";
		try {
			Account account = new Account(accessToken);
			JSONObject jsonObject = account.getUid();
			uid = String.valueOf(jsonObject.get("uid"));
			authInfo.setThirdparty(tp);
			authInfo.setToken(accessToken);
			authInfo.setTpIdentity(uid);
		} catch (Exception e) {
			log.error("weibo fetchTpIdentity is error" + e.getMessage());
		}
		return uid;
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		return true;
	}

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, String turnTo)
			throws UnsupportedEncodingException {
		String url = null;
		String redirectURL = tp.getAppUrl();
		if (StringUtils.isNotEmpty(turnTo)) {
			redirectURL = redirectURL + "?turnTo=" + turnTo;
		}
		try {
			Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
					URLEncoder.encode(redirectURL, Constants.UTF8));
			url = oauth.authorize("code");
		} catch (WeiboException e) {
		}
		return url;
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		List<String> list = new ArrayList<String>();
		if (authInfo != null) {
			Users users = new Users(authInfo.getToken());
			if (CollectionUtils.isNotEmpty(fuids)) {
				for (String fuid : fuids) {
					User user = null;
					try {
						user = users.showUserById(fuid);
					} catch (WeiboException e) {
						log.error("getInviteReceiverName is error"
								+ e.getMessage());
					}
					if (user != null) {
						list.add(user.getName());
					}
				}
			}
		}
		return list;
	}

}
