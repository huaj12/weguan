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

import com.juzhai.common.InitData;
import com.juzhai.core.Constants;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.Municipal;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.Town;
import com.juzhai.platform.bean.Terminal;

@Service
public class WeiboConnectUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(WeiboConnectUserService.class);
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;
	@Value(value = "${feature.length.max}")
	private int featureLengthMax;

	public AccessToken getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
				tp.getAppUrl());
		AccessToken accessToken = null;
		try {
			accessToken = oauth.getAccessTokenByCode(code);
		} catch (WeiboException e) {
			log.error("weibo getOAuthAccessTokenFromCode is error"
					+ e.getMessage());
		}
		if (accessToken == null
				|| StringUtils.isEmpty(accessToken.getAccessToken())
				|| StringUtils.isEmpty(accessToken.getExpireIn())) {
			return null;
		}
		return accessToken;
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		try {
			Users users = new Users(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getAppKey(),
					authInfo.getAppSecret());
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
			if (StringUtils.isEmpty(authInfo.getTokenSecret())) {
				profile.setNewLogoPic(user.getAvatarLarge());
			} else {
				profile.setNewLogoPic(user.getProfileImageUrl());
			}

			profile.setLogoVerifyState(LogoVerifyState.VERIFYING.getType());
			// profile.setLogoPic(user.getAvatarLarge());
			// 用户简介
			profile.setFeature(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(user.getDescription()),
					featureLengthMax, StringUtils.EMPTY));
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
			log.error("weibo get code is " + code);
			return null;
		}
		if (null == tp) {
			log.error("weibo  Thirdparty is null");
			return null;
		}
		AccessToken token = getOAuthAccessTokenFromCode(tp, code);
		if (null == token) {
			log.error("weibo  accessToken is null");
			return null;
		}
		String uid = null;
		try {
			long expiresTime = 0;
			String accessToken = token.getAccessToken();
			expiresTime = System.currentTimeMillis()
					+ Long.valueOf(token.getExpireIn()) * 1000;
			Account account = new Account(accessToken);
			JSONObject jsonObject = account.getUid();
			uid = String.valueOf(jsonObject.get("uid"));
			authInfo.setThirdparty(tp);
			authInfo.setToken(accessToken);
			authInfo.setTpIdentity(uid);
			authInfo.setExpiresTime(expiresTime);
		} catch (Exception e) {
			log.error("weibo fetchTpIdentity is error" + e.getMessage());
		}
		return uid;
	}

	@Override
	public String getAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode, String callbackUrl)
			throws UnsupportedEncodingException {
		String url = null;
		try {
			Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
					URLEncoder
							.encode(buildAuthorizeURLParams(callbackUrl,
									turnTo, incode), Constants.UTF8));
			url = oauth.authorize("code", terminal.getType());
		} catch (WeiboException e) {
			log.error("getAuthorizeURLforCode is error" + e.getMessage());
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
