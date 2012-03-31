package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.Link;
import com.google.gdata.data.douban.UserEntry;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.Municipal;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Thirdparty;

@Service
public class DoubanConnectUserService extends AbstractUserService {
	private final Log log = LogFactory.getLog(this.getClass());
	private static Map<String, String> tokenMap = new HashMap<String, String>();
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;
	@Value(value = "${feature.length.max}")
	private int featureLengthMax;

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, String turnTo,
			String incode) throws UnsupportedEncodingException {
		String url = null;
		DoubanService doubanService = new DoubanService(tp.getAppId(),
				tp.getAppKey(), tp.getAppSecret());
		url = doubanService.getAuthorizationUrl(buildAuthorizeURLParams(tp,
				turnTo, incode));
		tokenMap.put(doubanService.getRequestToken(),
				doubanService.getRequestTokenSecret());
		return url;
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		return Collections.emptyList();
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
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
			profile.setNickname(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(user.getTitle().getPlainText()),
					nicknameLengthMax, StringUtils.EMPTY));

			List<Link> links = user.getLinks();
			for (Link link : links) {
				if ("alternate".equals(link.getRel())) {
					// //获取用户个人主页
					profile.setBlog(link.getHref().replaceFirst("http://", ""));
				}
			}
			profile.setLogoVerifyState(LogoVerifyState.NONE.getType());
			// 用户简介
			profile.setFeature(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(user.getContent().getLang()),
					featureLengthMax, StringUtils.EMPTY));

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
	protected String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		String code = request.getParameter("oauth_token");
		if (StringUtils.isEmpty(code)) {
			log.error("douban get oauth_token is null");
			return null;
		}
		if (null == tp) {
			log.error("douban  Thirdparty is null");
			return null;
		}
		String accessToken = getOAuthAccessTokenFromCode(tp, code);
		if (StringUtils.isEmpty(accessToken)) {
			log.error("douban accessToken is null");
			return null;
		}
		String uid = "";
		try {
			String[] str = accessToken.split(",");
			DoubanService doubanService = DoubanService.getDoubanService(
					str[0], str[1], tp.getAppKey(), tp.getAppSecret(),
					tp.getAppId());
			UserEntry user = doubanService.getAuthorizedUser();
			uid = user.getUid();
			authInfo.setThirdparty(tp);
			authInfo.setToken(str[0]);
			authInfo.setTokenSecret(str[1]);
			authInfo.setTpIdentity(uid);
		} catch (Exception e) {
			log.error("douban fetchTpIdentity is error" + e.getMessage());
		}
		return uid;
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {

		return true;
	}

	@Override
	protected String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		DoubanService doubanService = new DoubanService(tp.getAppId(),
				tp.getAppKey(), tp.getAppSecret());
		doubanService.setRequestToken(code);
		doubanService.setRequestTokenSecret(tokenMap.get(code));
		ArrayList<String> list = null;
		try {
			list = doubanService.getAccessToken();
		} catch (Exception e) {
			log.error("douban content getOAuthAccessTokenFromCode is error."
					+ e.getMessage());
		}
		// 删除token_secret
		tokenMap.remove(code);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return StringUtils.join(list, ",");
	}

}
