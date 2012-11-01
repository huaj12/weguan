package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.Link;
import com.google.gdata.data.douban.UserEntry;
import com.juzhai.common.model.City;
import com.juzhai.common.model.Province;
import com.juzhai.core.util.IOSEmojiUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.bean.RequestParameter;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.Municipal;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;

@Service
public class DoubanConnectUserService extends AbstractUserService {
	private final Log log = LogFactory.getLog(this.getClass());
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;
	@Value(value = "${feature.length.max}")
	private int featureLengthMax;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${oauth.token.secret.expire.time}")
	private int oauthTokenSecretExpireTime;

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, Terminal terminal,
			String turnTo, String incode, String callbackUrl)
			throws UnsupportedEncodingException {
		String url = null;
		DoubanService doubanService = new DoubanService(tp.getAppId(),
				tp.getAppKey(), tp.getAppSecret());
		url = doubanService.getAuthorizationUrl(buildAuthorizeURLParams(
				callbackUrl, turnTo, incode));
		if (StringUtils.isEmpty(doubanService.getRequestTokenSecret())) {
			log.error("douban getAuthorizeURLforCode RequestTokenSecret is null");
			return null;
		}
		if (StringUtils.isEmpty(doubanService.getRequestToken())) {
			log.error("douban getAuthorizeURLforCode RequestToken is null");
			return null;
		}
		try {
			memcachedClient.set(doubanService.getRequestToken(),
					oauthTokenSecretExpireTime,
					doubanService.getRequestTokenSecret());
		} catch (Exception e) {
			log.error("douban getAuthorizeURLforCode memcachedClient is error");
		}
		return url;
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		return Collections.emptyList();
	}

	@Override
	protected Profile convertToProfile(RequestParameter requestParameter,
			AuthInfo authInfo, String thirdpartyIdentity) {
		try {
			Thirdparty tp = InitData.getTpByTpNameAndJoinType(
					authInfo.getThirdpartyName(),
					JoinTypeEnum.getJoinTypeEnum(authInfo.getJoinType()));
			DoubanService doubanService = DoubanService.getDoubanService(
					authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getAppKey(), authInfo.getAppSecret(),
					tp.getAppId());
			UserEntry user = doubanService.getAuthorizedUser();
			Profile profile = new Profile();
			profile.setNickname(TextTruncateUtil.truncate(IOSEmojiUtil
					.removeUtf8mb4Char(HtmlUtils.htmlUnescape(user.getTitle()
							.getPlainText())), nicknameLengthMax,
					StringUtils.EMPTY));

			List<Link> links = user.getLinks();
			for (Link link : links) {
				if ("alternate".equals(link.getRel())) {
					// //获取用户个人主页
					profile.setBlog(link.getHref().replaceFirst("http://", ""));
				}
			}
			profile.setLogoVerifyState(LogoVerifyState.NONE.getType());
			// 用户简介
			profile.setFeature(TextTruncateUtil.truncate(IOSEmojiUtil
					.removeUtf8mb4Char(HtmlUtils.htmlUnescape(user.getContent()
							.getLang())), featureLengthMax, StringUtils.EMPTY));

			City city = null;
			String cityName = user.getLocation();
			if (StringUtils.isNotEmpty(cityName)) {
				if (Municipal.getMunicipalEnum(cityName) != null) {
					city = com.juzhai.common.InitData.getCityByName(cityName);
				} else {
					// 非直辖市
					for (Province p : com.juzhai.common.InitData.PROVINCE_MAP
							.values()) {
						if (cityName.startsWith(p.getName())) {
							city = com.juzhai.common.InitData
									.getCityByName(cityName.substring(p
											.getName().length()));
						}
					}
				}
			}
			if (null != city) {
				profile.setCity(city.getId());
				profile.setProvince(city.getProvinceId());
			}
			return profile;
		} catch (Exception e) {
			log.error("douban content convertToProfile is error."
					+ e.getMessage());
			return null;
		}
	}

	@Override
	protected String fetchTpIdentity(RequestParameter requestParameter,
			AuthInfo authInfo, Thirdparty tp) {
		String oauthToken = requestParameter.get("oauth_token");
		if (StringUtils.isEmpty(oauthToken)) {
			log.error("douban get oauth_token is null");
			return null;
		}
		if (null == tp) {
			log.error("douban  Thirdparty is null");
			return null;
		}
		String oauthTokenSecret = null;
		try {
			oauthTokenSecret = memcachedClient.get(oauthToken);
			memcachedClient.delete(oauthToken);
		} catch (Exception e) {
			log.error("douban fetchTpIdentity memcachedCilent is error", e);
		}
		if (StringUtils.isEmpty(oauthTokenSecret)) {
			// String Agent = request.getHeader("User-Agent");
			// log.info("user Agent is " + Agent);
			log.error("douban  oauthTokenSecret is null");
			return null;
		}
		String accessToken = getOAuthAccessTokenFromCode(tp, oauthToken,
				oauthTokenSecret);
		if (StringUtils.isEmpty(accessToken)) {
			log.error("douban accessToken is null");
			return null;
		}
		String uid = null;
		try {
			String[] str = accessToken.split(",");
			if (null == str || str.length != 2 || StringUtils.isEmpty(str[0])
					|| StringUtils.isEmpty(str[1])) {
				log.error("douban getOAuthAccessTokenFromCode return  null");
				return null;
			}
			DoubanService doubanService = DoubanService.getDoubanService(
					str[0], str[1], tp.getAppKey(), tp.getAppSecret(),
					tp.getAppId());
			UserEntry user = doubanService.getAuthorizedUser();
			uid = doubanService.getUid(user.getId(),
					"http://api.douban.com/people/(\\d*)");
			if (StringUtils.isEmpty(uid)) {
				log.error("douban getUid is null");
				return null;
			}
			authInfo.setThirdparty(tp);
			authInfo.setToken(str[0]);
			authInfo.setTokenSecret(str[1]);
			authInfo.setTpIdentity(uid);
		} catch (Exception e) {
			log.error("douban fetchTpIdentity is error" + e.getMessage());
		}
		return uid;
	}

	private String getOAuthAccessTokenFromCode(Thirdparty tp, String token,
			String tokenSecret) {
		DoubanService doubanService = new DoubanService(tp.getAppId(),
				tp.getAppKey(), tp.getAppSecret());
		doubanService.setRequestToken(token);
		doubanService.setRequestTokenSecret(tokenSecret);
		ArrayList<String> list = null;
		try {
			list = doubanService.getAccessToken();
		} catch (Exception e) {
			log.error("douban content getOAuthAccessTokenFromCode is error."
					+ e.getMessage());
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return StringUtils.join(list, ",");
	}

}
