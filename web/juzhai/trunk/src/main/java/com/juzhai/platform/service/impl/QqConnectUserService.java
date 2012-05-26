package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.service.ISynchronizeService;
import com.qq.connect.AccessToken;
import com.qq.connect.InfoToken;
import com.qq.connect.RedirectToken;
import com.qq.connect.RequestToken;

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
	@Value("${oauth.token.secret.expire.time}")
	private int oauthTokenSecretExpireTime;

	@Override
	public String getAuthorizeURLforCode(HttpServletRequest request,
			Thirdparty tp, String turnTo, String incode)
			throws UnsupportedEncodingException {
		String url = null;
		try {
			RequestToken rt = new RequestToken(tp.getAppKey(),
					tp.getAppSecret());
			Map<String, String> tokens = rt.getRequestToken();
			if (StringUtils.isEmpty(tokens.get("oauth_token_secret"))) {
				log.error("qq getAuthorizeURLforCode oauth_token_secret is null");
				return null;
			}
			if (StringUtils.isEmpty(tokens.get("oauth_token"))) {
				log.error("qq getAuthorizeURLforCode oauth_token is null");
				return null;
			}
			memcachedClient.set(tokens.get("oauth_token"),
					oauthTokenSecretExpireTime,
					tokens.get("oauth_token_secret"));
			RedirectToken ret = new RedirectToken(tp.getAppKey(),
					tp.getAppSecret());
			url = ret.getRedirectURL(tokens,
					buildAuthorizeURLParams(tp, turnTo, incode));
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
			InfoToken info = new InfoToken(authInfo.getAppKey(),
					authInfo.getAppSecret());
			Map<String, String> map = info.getInfo(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getTpIdentity());
			Profile profile = new Profile();
			profile.setNickname(TextTruncateUtil.truncate(
					HtmlUtils.htmlUnescape(map.get("nickname")),
					nicknameLengthMax, StringUtils.EMPTY));
			profile.setNewLogoPic(map.get("figureurl_2"));
			profile.setLogoVerifyState(LogoVerifyState.VERIFYING.getType());

			String male = messageSource.getMessage("gender.male", null,
					Locale.SIMPLIFIED_CHINESE);
			if (male.equals(map.get("gender"))) {
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
			log.equals("QQ content convertToProfile is error." + e.getMessage());
			return null;
		}
	}

	@Override
	protected String fetchTpIdentity(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		String oauth_token = request.getParameter("oauth_token");
		if (StringUtils.isEmpty(oauth_token)) {
			log.error("QQ  oauth_token is null");
			return null;
		}
		String oauth_vericode = request.getParameter("oauth_vericode");
		if (StringUtils.isEmpty(oauth_vericode)) {
			log.error("QQ  oauth_vericode is null");
			return null;
		}
		if (null == tp) {
			log.error("QQ  Thirdparty is null");
			return null;
		}
		String oauthTokenSecret = null;
		try {
			oauthTokenSecret = memcachedClient.get(oauth_token);
			memcachedClient.delete(oauth_token);
		} catch (Exception e) {
			log.error("QQ fetchTpIdentity memcachedCilent is error", e);
		}
		if (StringUtils.isEmpty(oauthTokenSecret)) {
			// String Agent = request.getHeader("User-Agent");
			// log.info("user Agent is " + Agent);
			// log.error("QQ  oauthTokenSecret is null");
			return null;
		}
		String accessToken = getOAuthAccessTokenFromCode(tp, oauth_token + ","
				+ oauth_vericode + "," + oauthTokenSecret);
		if (StringUtils.isEmpty(accessToken)) {
			log.error("QQ  accessToken is null");
			return null;
		}
		String[] str = accessToken.split(",");
		String uid = str[2];
		authInfo.setThirdparty(tp);
		authInfo.setToken(str[0]);
		authInfo.setTokenSecret(str[1]);
		authInfo.setTpIdentity(uid);
		return uid;
	}

	@Override
	protected boolean checkAuthInfo(HttpServletRequest request,
			AuthInfo authInfo, Thirdparty tp) {
		return true;
	}

	@Override
	protected String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		String[] str = code.split(",");
		String oauth_token = str[0];
		String oauth_vericode = str[1];
		String oauthTokenSecret = str[2];
		String accessToken = null;
		try {
			Map<String, String> map = new AccessToken(tp.getAppKey(),
					tp.getAppSecret()).getAccessToken(oauth_token,
					oauthTokenSecret, oauth_vericode);
			accessToken = map.get("oauth_token") + ","
					+ map.get("oauth_token_secret") + "," + map.get("openid");
		} catch (Exception e) {
			log.equals("QQ content getOAuthAccessTokenFromCode is error."
					+ e.getMessage());
		}
		return accessToken;
	}
}
