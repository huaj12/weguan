package com.juzhai.platform.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.qq.connect.AccessToken;
import com.qq.connect.InfoToken;
import com.qq.connect.RedirectToken;
import com.qq.connect.RequestToken;
import com.qq.util.ParseString;

@Service
public class QqConnectUserService extends AbstractUserService {
	private final Log log = LogFactory.getLog(getClass());
	private static Map<String, String> tokenMap = new HashMap<String, String>();
	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;
	@Autowired
	private MessageSource messageSource;

	@Override
	public String getAuthorizeURLforCode(Thirdparty tp, String turnTo)
			throws UnsupportedEncodingException {
		String url = null;
		String redirectURL = tp.getAppUrl();
		if (StringUtils.isNotEmpty(turnTo)) {
			redirectURL = redirectURL + "?turnTo=" + turnTo;
		}
		try {
			RequestToken rt = new RequestToken(tp.getAppKey(),
					tp.getAppSecret());
			String requesttoken = rt.getRequestToken();
			HashMap<String, String> tokens = ParseString
					.parseTokenString(requesttoken);
			tokenMap.put(tokens.get("oauth_token"),
					tokens.get("oauth_token_secret"));
			RedirectToken ret = new RedirectToken(tp.getAppKey(),
					tp.getAppSecret());
			url = ret.getRedirectURL(tokens, redirectURL);
		} catch (Exception e) {
			log.equals("QQ content getAuthorizeURLforCode is error."
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
			profile.setLogoVerifyState(LogoVerifyState.NONE.getType());

			String male = messageSource.getMessage("gender.male", null,
					Locale.SIMPLIFIED_CHINESE);
			if (male.equals(map.get("gender"))) {
				profile.setGender(1);
			} else {
				profile.setGender(0);
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
			return null;
		}
		String oauth_vericode = request.getParameter("oauth_vericode");
		if (StringUtils.isEmpty(oauth_vericode)) {
			return null;
		}
		if (null == tp) {
			return null;
		}
		String accessToken = getOAuthAccessTokenFromCode(tp, oauth_token + ","
				+ oauth_vericode);
		if (StringUtils.isEmpty(accessToken)) {
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
		String accessToken = "";
		try {
			String content = new AccessToken(tp.getAppKey(), tp.getAppSecret())
					.getAccessToken(oauth_token, tokenMap.get(oauth_token),
							oauth_vericode);
			Map<String, String> map = ParseString.parseTokenString(content);
			accessToken += map.get("oauth_token");
			accessToken += "," + map.get("oauth_token_secret");
			accessToken += "," + map.get("openid");
		} catch (Exception e) {
			log.equals("QQ content getOAuthAccessTokenFromCode is error."
					+ e.getMessage());
		}
		return accessToken;
	}

}
