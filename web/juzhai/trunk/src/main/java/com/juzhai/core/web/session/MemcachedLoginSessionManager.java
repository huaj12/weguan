package com.juzhai.core.web.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.web.cookies.CookiesManager;

@Component(value = "loginSessionManager")
public class MemcachedLoginSessionManager extends AbstractLoginSessionManager {

	private final String token_cookies_name = "l_token";

	@Autowired
	private MemcachedClient memcachedClient;

	@Override
	public UserContext getUserContext(HttpServletRequest request) {
		Long uid = null;
		String sessionId = null;
		Long tpId = null;
		Boolean admin = null;

		String token = CookiesManager.getCookie(request, token_cookies_name);
		if (StringUtils.isNotEmpty(token)) {
			try {
				Map<String, Object> map = memcachedClient.get(token);
				if (null != map) {
					uid = (Long) map.get(UID_ATTRIBUTE_NAME);
					sessionId = token;
					tpId = (Long) map.get(TPID_ATTRIBUTE_NAME);
					admin = (Boolean) map.get(ADMIN_ATTRIBUTE_NAME);
					memcachedClient.set(token, loginExpireTimeSeconds, map);
				}
			} catch (Exception e) {
				log.error("update login expire error", e);
			}
		}
		return getUserContext(request, uid, sessionId, tpId, admin);
	}

	@Override
	public void updateLoginExpire(HttpServletRequest request) {
	}

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response,
			long uid, long tpId, boolean isAdmin, boolean persistent) {
		String token = UUID.randomUUID().toString();
		CookiesManager.setCookie(request, response, token_cookies_name, token,
				-1);
		Map<String, Object> map = new HashMap<String, Object>(3 * 4 / 3);
		map.put(UID_ATTRIBUTE_NAME, uid);
		map.put(TPID_ATTRIBUTE_NAME, tpId);
		map.put(ADMIN_ATTRIBUTE_NAME, isAdmin);

		try {
			memcachedClient.set(token, loginExpireTimeSeconds, map);
		} catch (Exception e) {
			log.error("login error.", e);
		}
		if (persistent) {
			persistLogin(request, response, uid);
		}
		// request里保存userContext
		request.setAttribute("context",
				getUserContext(request, uid, token, tpId, isAdmin));
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String token = CookiesManager.getCookie(request, token_cookies_name);
		if (StringUtils.isNotEmpty(token)) {
			try {
				memcachedClient.delete(token);
			} catch (Exception e) {
				log.error("logout error", e);
			}
			CookiesManager.delCookie(request, response, token_cookies_name);
		}
		delPersistLogin(request, response);
	}
}
