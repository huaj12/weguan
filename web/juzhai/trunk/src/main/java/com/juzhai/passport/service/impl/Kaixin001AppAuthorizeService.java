package com.juzhai.passport.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kx4j.KxException;
import kx4j.KxSDK;
import kx4j.User;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;

/**
 * @author wujiajun Created on 2011-2-17
 */
@Service
public class Kaixin001AppAuthorizeService extends AbstractAuthorizeService {
	private static final Log log = LogFactory
			.getLog(Kaixin001AppAuthorizeService.class);

	@Value(value = "${nickname.length.max}")
	private int nicknameLengthMax;

	// @Override
	// public AuthInfo requestAuthInfo(Thirdparty tp, AuthorizeInfo
	// authorizeInfo) {
	// throw new UnsupportedOperationException(
	// "kaixin app authorize unsupport requestAuthInfo.");
	// }

	@Override
	public long access(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp) {
		String sessionKey = request.getParameter("session_key");
		if (StringUtils.isEmpty(sessionKey)) {
			return 0L;
		}
		long time=System.currentTimeMillis();
		KxSDK kxSDK = newKxSDK(tp.getAppKey(), tp.getAppSecret(), sessionKey);
		User user = null;
		try {
			user = kxSDK.getMyInfo("uid");
		} catch (KxException e) {
			log.error(e.getMessage(), e);
		}
		System.out.println(System.currentTimeMillis()-time);
		if (null == user || user.getUid() <= 0) {
			return 0L;
		}
		// 构建authInfo
		authInfo = new AuthInfo(tp, sessionKey);
		return completeAccessUser(request, response, authInfo,
				String.valueOf(user.getUid()), tp);
	}

	@Override
	protected Profile convertToProfile(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo,
			String thirdpartyIdentity, Thirdparty tp) {
		// 调用开心API
		KxSDK kxSDK = newKxSDK(tp.getAppKey(), tp.getAppSecret(),
				authInfo.getSessionKey());
		String[] fields = new String[] { "name", "gender", "hometown", "city",
				"logo120", "bodyform", "interest", "school", "company" };
		User user = null;
		try {
			user = kxSDK.getMyInfo(StringUtils.join(fields, ","));
		} catch (KxException e) {
			log.error(e.getMessage(), e);
		}
		return kxUserToProfile(user);
	}

	private Profile kxUserToProfile(User user) {
		if (null == user) {
			return null;
		}
		Profile profile = new Profile();
		profile.setNickname(TextTruncateUtil.truncate(
				HtmlUtils.htmlUnescape(user.getName()), nicknameLengthMax,
				StringUtils.EMPTY));
		profile.setGender(Math.abs(user.getGender() - 1));
		profile.setHome(HtmlUtils.htmlEscape(user.getHometown()));
		profile.setLogoPic(user.getLogo120());
		profile.setShape(HtmlUtils.htmlEscape(user.getBodyform()));
		return profile;
	}

	private KxSDK newKxSDK(String key, String secret, String sessionKey) {
		KxSDK kxSDK = new KxSDK();
		kxSDK.setOAuthConsumer(key, secret);
		kxSDK.setToken(sessionKey, "kaixin001");
		return kxSDK;
	}
}