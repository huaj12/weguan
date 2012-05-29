package com.juzhai.core.web.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.web.cookies.CookiesManager;

public abstract class AbstractLoginSessionManager implements
		LoginSessionManager {

	protected final Log log = LogFactory.getLog(getClass());
	protected final String persist_token_cookies_name = "p_token";
	protected final String persist_token_separator = "_";
	protected final String UID_ATTRIBUTE_NAME = "uid";
	protected final String TPID_ATTRIBUTE_NAME = "tpId";
	protected final String ADMIN_ATTRIBUTE_NAME = "admin";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Value(value = "${login.expire.time.seconds}")
	protected int loginExpireTimeSeconds;
	@Value(value = "${persist.login.expire.time.seconds}")
	private int persistLoginExpireTimeSeconds;

	@Override
	public long persistentLoginUid(HttpServletRequest request,
			HttpServletResponse response) {
		return checkPersistLogin(request, response);
	}

	protected void persistLogin(HttpServletRequest request,
			HttpServletResponse response, long uid) {
		String persistToken = UUID.randomUUID().toString();
		String value = uid + persist_token_separator + persistToken;
		CookiesManager.setCookie(request, response, persist_token_cookies_name,
				value, persistLoginExpireTimeSeconds);
		String key = RedisKeyGenerator.genPersistLoginTokenKey(uid);
		redisTemplate.opsForValue().set(key, persistToken);
		redisTemplate.expire(key, persistLoginExpireTimeSeconds,
				TimeUnit.SECONDS);
	}

	protected long checkPersistLogin(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Long, String> tokenMap = getPersistLoginToken(request);
		if (tokenMap.size() == 1) {
			long uid = 0L;
			String token = null;
			for (Map.Entry<Long, String> entry : tokenMap.entrySet()) {
				uid = entry.getKey();
				token = entry.getValue();
			}
			if (StringUtils.equals(
					token,
					redisTemplate.opsForValue().get(
							RedisKeyGenerator.genPersistLoginTokenKey(uid)))) {

				return uid;
			}
		}
		return 0L;
	}

	protected void delPersistLogin(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Long, String> tokenMap = getPersistLoginToken(request);
		if (tokenMap.size() == 1) {
			long uid = 0L;
			String token = null;
			for (Map.Entry<Long, String> entry : tokenMap.entrySet()) {
				uid = entry.getKey();
				token = entry.getValue();
			}
			if (uid > 0 && StringUtils.isNotEmpty(token)) {
				String key = RedisKeyGenerator.genPersistLoginTokenKey(uid);
				redisTemplate.delete(key);
			}
			CookiesManager.delCookie(request, response,
					persist_token_cookies_name);
		}
	}

	protected Map<Long, String> getPersistLoginToken(HttpServletRequest request) {
		String value = CookiesManager.getCookie(request,
				persist_token_cookies_name);
		if (StringUtils.isNotEmpty(value)) {
			StringTokenizer st = new StringTokenizer(value,
					persist_token_separator);
			if (st.countTokens() == 2) {
				Map<Long, String> map = new HashMap<Long, String>(2);
				map.put(Long.valueOf(st.nextToken()), st.nextToken());
				return map;
			}
		}
		return Collections.emptyMap();
	}
}
