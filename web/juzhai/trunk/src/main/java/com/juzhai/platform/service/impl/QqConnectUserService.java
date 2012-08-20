package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.cookies.CookiesManager;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.ISynchronizeService;
import com.qq.oauth2.Oauth;
import com.qq.oauth2.User;
import com.qq.oauth2.bean.UserInfoBean;

@Service
public class QqConnectUserService extends AbstractUserService {
	private final Log log = LogFactory.getLog(getClass());
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ISynchronizeService synchronizeService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${user.state.id.expire.time}")
	private int userStateIdExpireTime;

	@Override
	public String getAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode, String callbackUrl)
			throws UnsupportedEncodingException {
		String url = null;
		try {
			Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
					tp.getAppUrl());
			String state = String.valueOf(System.currentTimeMillis());
			String stateId = UUID.randomUUID().toString();
			CookiesManager.setCookie(request, response,
					CookiesManager.STATE_NAME, stateId, -1);
			url = oauth.authorize(terminal.getType(), state, callbackUrl);
			memcachedClient.add(stateId, userStateIdExpireTime, state);
		} catch (Exception e) {
			log.error("QQ content getAuthorizeURLforCode is error."
					+ e.getMessage());
		}
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
			User user = new User(authInfo.getToken(), authInfo.getAppKey(),
					authInfo.getAppSecret());
			UserInfoBean userInfo = user.getUserInfo(authInfo.getTpIdentity());
			if (userInfo.isError()) {
				log.error("QQ content getuser info is error. msg="
						+ userInfo.getErrorMsg() + " code="
						+ userInfo.getErrorCode());
				return null;
			}
			Profile profile = new Profile();
			profile.setNickname(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(userInfo.getNickName()),
					nicknameLengthMax, StringUtils.EMPTY));
			profile.setNewLogoPic(userInfo.getAvatarLarge());
			profile.setLogoVerifyState(LogoVerifyState.VERIFYING.getType());

			String male = messageSource.getMessage("gender.male", null,
					Locale.SIMPLIFIED_CHINESE);
			if (male.equals(userInfo.getGender())) {
				profile.setGender(1);
			} else {
				profile.setGender(0);
			}
			try {
				// 注册成功分享
				String title = messageSource.getMessage(
						"qq.register.share.title", null,
						Locale.SIMPLIFIED_CHINESE);
				String link = messageSource.getMessage(
						"qq.register.share.link", null,
						Locale.SIMPLIFIED_CHINESE);
				String imageUrl = messageSource.getMessage(
						"qq.register.share.imgUrl", null,
						Locale.SIMPLIFIED_CHINESE);
				synchronizeService.sendMessage(authInfo, title, null, link,
						null, JzResourceFunction.u(imageUrl));
			} catch (Exception e) {
				log.error("QQ register share is error");
			}
			return profile;
		} catch (Exception e) {
			log.error("QQ content convertToProfile is error." + e.getMessage());
			return null;
		}
	}

	@Override
	protected String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code)) {
			log.error("QQ  code is null");
			return null;
		}
		String state = request.getParameter("state");
		if (StringUtils.isEmpty(state)) {
			log.error("QQ  state is null");
			return null;
		}
		String stateId = CookiesManager.getCookie(request,
				CookiesManager.STATE_NAME);
		String localState = null;
		if (StringUtils.isNotEmpty(stateId)) {
			try {
				localState = memcachedClient.get(stateId);
				// qq连点bug缩短stateId过期时间
				// memcachedClient.delete(stateId);
			} catch (Exception e) {
				log.error("memcached get state is error", e);
			}
		}
		if (localState == null || !localState.equals(state)) {
			// log.error("stateId:" + stateId);
			// log.error("localState:" + localState);
			// log.error("state:" + state);
			// log.error(request.getHeader("User-Agent"));
			// log.error(request.getHeader("Referer"));
			// log.error("state is not from QQ");
			return null;
		}
		Oauth oauth = new Oauth(tp.getAppKey(), tp.getAppSecret(),
				tp.getAppUrl());
		String str[] = null;
		String accessToken = null;
		long expiresTime = 0;
		try {
			str = oauth.getAccessToken(code, state);
			if (str == null || str.length != 2 || StringUtils.isEmpty(str[0])
					|| StringUtils.isEmpty(str[1])) {
				log.error("QQ content getAccessToken return null");
				return null;
			}
			accessToken = str[0];
			expiresTime = System.currentTimeMillis() + Long.valueOf(str[1])
					* 1000;
		} catch (Exception e) {
			log.error("QQ content get accessToken is error." + e.getMessage());
		}
		if (StringUtils.isEmpty(accessToken)) {
			log.error("QQ  accessToken is null");
			return null;
		}
		String uid = null;
		try {
			uid = oauth.getOpenId(accessToken);
		} catch (Exception e) {
			log.error("qq content get openid is error.", e);
		}
		authInfo.setThirdparty(tp);
		authInfo.setToken(accessToken);
		authInfo.setTpIdentity(uid);
		authInfo.setExpiresTime(expiresTime);
		return uid;
	}

}
