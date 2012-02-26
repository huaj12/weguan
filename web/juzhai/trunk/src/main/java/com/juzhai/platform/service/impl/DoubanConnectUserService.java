package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gdata.client.douban.DoubanService;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.douban.UserEntry;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.Municipal;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.Town;

@Service
public class DoubanConnectUserService extends AbstractUserService {
	private final Log log = LogFactory.getLog(this.getClass());
	private static Map<String, String> tokenMap = new HashMap<String, String>();
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, String turnTo)
			throws UnsupportedEncodingException {
		String url = null;
		String redirectURL = tp.getAppUrl();
		if (StringUtils.isNotEmpty(turnTo)) {
			redirectURL = redirectURL + "?turnTo=" + turnTo;
		}
		DoubanService doubanService = new DoubanService("51juzhai",
				tp.getAppKey(), tp.getAppSecret());
		url = doubanService.getAuthorizationUrl(redirectURL);
		tokenMap.put(doubanService.getRequestToken(),
				doubanService.getRequestTokenSecret());
		return url;
	}

	@Override
	public List<String> getUserNames(AuthInfo authInfo, List<String> fuids) {
		List<String> list = new ArrayList<String>();
		if (authInfo != null) {
			DoubanService doubanService = DoubanService.getDoubanService(
					authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getAppKey(), authInfo.getAppSecret());
			if (CollectionUtils.isNotEmpty(fuids)) {
				for (String fuid : fuids) {
					UserEntry user = null;
					try {
						user = doubanService.getUser(fuid);
					} catch (Exception e) {
						log.error("douban getInviteReceiverName is error"
								+ e.getMessage());
					}
					if (user != null) {
						TextConstruct title = user.getTitle();
						list.add(title.getPlainText());
					}
				}
			}
		}
		return list;
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity) {
		try {
			DoubanService doubanService = DoubanService.getDoubanService(
					authInfo.getToken(), authInfo.getTokenSecret(),
					authInfo.getAppKey(), authInfo.getAppSecret());
			UserEntry user = doubanService.getAuthorizedUser();
			Profile profile = new Profile();
			profile.setNickname(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(user.getTitle().getPlainText()),
					nicknameLengthMax, StringUtils.EMPTY));

			List<Link> links = user.getLinks();
			for (Link link : links) {
				if ("icon".equals(link.getRel())) {
					profile.setNewLogoPic(link.getHref());
				} else if ("homepage".equals(link.getRel())) {
					// //获取用户个人主页
					profile.setBlog(link.getHref());
				}
			}

			profile.setLogoVerifyState(LogoVerifyState.VERIFYING.getType());
			// 用户简介
			profile.setFeature(user.getContent().getLang());
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
		String code = request.getParameter("oauth_token");
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
			String[] str = accessToken.split(",");
			DoubanService doubanService = DoubanService.getDoubanService(
					str[0], str[1], tp.getAppKey(), tp.getAppSecret());
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
		DoubanService doubanService = new DoubanService("51juzhai",
				tp.getAppKey(), tp.getAppSecret());
		doubanService.setRequestToken(code);
		doubanService.setRequestTokenSecret(tokenMap.get(code));
		ArrayList<String> list = doubanService.getAccessToken();
		// 删除token_secret
		tokenMap.remove(code);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return StringUtils.join(list, ",");
	}

}
