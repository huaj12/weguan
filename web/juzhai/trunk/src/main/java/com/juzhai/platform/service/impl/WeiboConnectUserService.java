package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.http.v1.RequestToken;
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
import com.juzhai.platform.Version;
import com.juzhai.platform.bean.Terminal;

@Service
public class WeiboConnectUserService extends AbstractUserService {
	private static final Log log = LogFactory
			.getLog(WeiboConnectUserService.class);
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;
	@Value(value = "${feature.length.max}")
	private int featureLengthMax;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${oauth.token.secret.expire.time}")
	private int oauthTokenSecretExpireTime;

	@Override
	public String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
				tp.getAppUrl());
		String accessToken = null;

		String str[] = code.split(",");
		try {
			if (str[0].equals("null")) {
				String oauth_token = str[1];
				String oauth_verifier = str[2];
				String tokenSecret = null;
				try {
					tokenSecret = memcachedClient.get(oauth_token);
					memcachedClient.delete(oauth_token);
				} catch (Exception e) {
					log.error(
							"weibo getOAuthAccessTokenFromCode memcachedClient is error.",
							e);
					return null;
				}
				if (StringUtils.isEmpty(tokenSecret)) {
					log.error("weibo tokenSecret is null");
					return null;
				}
				weibo4j.http.v1.AccessToken token = oauth.getOAuthAccessToken(
						oauth_token, tokenSecret, oauth_verifier);
				StringBuffer sb = new StringBuffer();
				sb.append(token.getToken());
				sb.append(",");
				sb.append(token.getTokenSecret());
				sb.append(",");
				sb.append(token.getUserId());
				accessToken = sb.toString();
			} else {
				AccessToken token = oauth.getAccessTokenByCode(str[0]);
				accessToken = token.getAccessToken();
			}
		} catch (WeiboException e) {
			log.error("weibo getOAuthAccessTokenFromCode is error"
					+ e.getMessage());
		}
		if (null == accessToken) {
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
		String oauth_token = request.getParameter("oauth_token");
		String oauth_verifier = request.getParameter("oauth_verifier");

		if (StringUtils.isEmpty(code) && StringUtils.isEmpty(oauth_token)
				&& StringUtils.isEmpty(oauth_verifier)) {
			log.error("weibo get code is " + code + ":oauth_token="
					+ oauth_token + ":oauth_verifier=" + oauth_verifier);
			return null;
		}
		if (null == tp) {
			log.error("weibo  Thirdparty is null");
			return null;
		}
		String token = getOAuthAccessTokenFromCode(tp, code + "," + oauth_token
				+ "," + oauth_verifier);
		if (StringUtils.isEmpty(token)) {
			log.error("weibo  accessToken is null");
			return null;
		}

		String uid = "";
		try {
			String[] str = token.split(",");
			String accessToken = null;
			String tokenSecret = null;
			if (str.length > 1) {
				accessToken = str[0];
				tokenSecret = str[1];
				uid = str[2];
			} else {
				accessToken = token;
				Account account = new Account(accessToken);
				JSONObject jsonObject = account.getUid();
				uid = String.valueOf(jsonObject.get("uid"));
			}
			authInfo.setThirdparty(tp);
			authInfo.setToken(accessToken);
			authInfo.setTpIdentity(uid);
			authInfo.setTokenSecret(tokenSecret);
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
	public String getAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException {
		String url = null;
		try {
			Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
					URLEncoder.encode(
							buildAuthorizeURLParams(tp, turnTo, incode),
							Constants.UTF8));
			if (Version.WEIBOV2.equals(Version.getWeiboVersion())) {
				url = oauth.authorize("code", terminal.getType());
			} else {
				RequestToken requestToken = oauth.getRequestToken();
				try {
					memcachedClient.set(requestToken.getToken(),
							oauthTokenSecretExpireTime,
							requestToken.getTokenSecret());
				} catch (Exception e) {
					log.error(
							"weibo getAuthorizeURLforCode memcachedClient is error",
							e);
					return null;
				}
				url = oauth.getAuthorizeV1(requestToken);
			}
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
