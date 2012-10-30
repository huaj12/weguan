package com.juzhai.android.passport.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.juzhai.android.core.SystemConfig;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.passport.model.User;

public class UserCacheManager {

	private final static String P_TOKEN_NAME = "p_token";
	private final static String L_TOKEN_NAME = "l_token";
	private final static String UID_NAME = "uid";
	private final static String SEMICOLON = ";";
	private final static String EQUALSIGN = "=";

	public static void cache(Context context,
			ResponseEntity<UserResult> responseEntity) {
		SystemConfig config = (SystemConfig) context.getApplicationContext();
		UserCache.setUserInfo(responseEntity.getBody().getResult());
		List<String> cookieHeaders = responseEntity.getHeaders().get(
				"Set-Cookie");
		if (null == cookieHeaders) {
			cookieHeaders = new ArrayList<String>(1);
		}
		Map<String, Map<String, String>> cookies = parseCookies(cookieHeaders);
		if (!cookies.containsKey(L_TOKEN_NAME)) {
			String lTokenString = newLTokenString(context, config);
			if (StringUtils.hasText(lTokenString)) {
				cookieHeaders.add(lTokenString);
				putParseCookie(cookies, lTokenString);
			}
		}
		Map<String, String> pTokenMap = cookies.get(P_TOKEN_NAME);
		Map<String, String> lTokenMap = cookies.get(L_TOKEN_NAME);
		if (lTokenMap != null) {
			String lToken = lTokenMap.get(L_TOKEN_NAME);
			if (StringUtils.hasText(lToken)) {
				UserCache.setlToken(lToken);
			}
		}
		if (pTokenMap != null) {
			String pToken = pTokenMap.get(P_TOKEN_NAME);
			if (StringUtils.hasText(pToken)) {
				UserCache.setpToken(pToken);
			}
		}

		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		// cookieManager.removeSessionCookie();
		if (null != cookieHeaders) {
			for (String cookie : cookieHeaders) {
				cookieManager.setCookie(config.getBaseUrl(), cookie);
			}
		}
		CookieSyncManager.getInstance().sync();
	}

	public static void updateUserCache(User user) {
		UserCache.setUserInfo(user);
	}

	public static void persistInfo(Context context,
			ResponseEntity<UserResult> responseEntity) {
		Map<String, Map<String, String>> cookies = parseCookies(responseEntity
				.getHeaders().get("Set-Cookie"));
		if (cookies.get(P_TOKEN_NAME) != null) {
			String pToken = cookies.get(P_TOKEN_NAME).get(P_TOKEN_NAME);
			new SharedPreferencesManager(context).commit(P_TOKEN_NAME, pToken);
		}
		if (cookies.get(L_TOKEN_NAME) != null) {
			String lToken = cookies.get(L_TOKEN_NAME).get(L_TOKEN_NAME);
			new SharedPreferencesManager(context).commit(L_TOKEN_NAME, lToken);
		}
		long uid = responseEntity.getBody().getResult().getUid();
		if (uid > 0) {
			new SharedPreferencesManager(context).commit(UID_NAME,
					String.valueOf(uid));
		}
	}

	public static String getPersistToken(Context context) {
		return new SharedPreferencesManager(context).getString(P_TOKEN_NAME);
	}

	public static void clearPersistToken(Context context) {
		new SharedPreferencesManager(context).delete(P_TOKEN_NAME);
	}

	public static String getPersistLToken(Context context) {
		return new SharedPreferencesManager(context).getString(L_TOKEN_NAME);
	}

	public static void clearPersistLToken(Context context) {
		new SharedPreferencesManager(context).delete(L_TOKEN_NAME);
	}

	public static long getPersistUid(Context context) {
		String str = new SharedPreferencesManager(context).getString(UID_NAME);
		if (!StringUtils.hasText(str)) {
			return 0;
		}
		return Long.valueOf(str);
	}

	public static void clearPersistUid(Context context) {
		new SharedPreferencesManager(context).delete(UID_NAME);
	}

	public static void localLogout(Context context) {
		UserCache.clear();
		clearPersistToken(context);
		clearPersistLToken(context);
		clearPersistUid(context);
	}

	private static Map<String, Map<String, String>> parseCookies(
			List<String> list) {
		Map<String, Map<String, String>> cookies = new HashMap<String, Map<String, String>>();
		if (null != list) {
			for (String str : list) {
				putParseCookie(cookies, str);
			}
		}
		return cookies;
	}

	protected static void putParseCookie(
			Map<String, Map<String, String>> cookies, String str) {
		String[] cookiesStr = str.split(SEMICOLON);
		Map<String, String> map = new HashMap<String, String>();
		String key = null;
		for (String cookieStr : cookiesStr) {
			String[] values = cookieStr.split(EQUALSIGN);
			if (values != null && values.length > 1) {
				if (null == key) {
					key = values[0];
				}
				map.put(values[0], values[1]);
			}
		}
		cookies.put(key, map);
	}

	private static String newLTokenString(Context context, SystemConfig config) {
		String lToken = UserCacheManager.getPersistLToken(context);
		if (StringUtils.hasText(lToken)) {
			StringBuilder sb = new StringBuilder();
			sb.append(L_TOKEN_NAME);
			sb.append(EQUALSIGN);
			sb.append(lToken);
			sb.append(SEMICOLON);
			sb.append("Domain");
			sb.append(EQUALSIGN);
			sb.append(config.getBaseUrl());
			sb.append(SEMICOLON);
			sb.append("Path");
			sb.append(EQUALSIGN);
			sb.append("/");
			return sb.toString();
		}
		return null;
	}

	public static <T> void updateLToken(Context context,
			ResponseEntity<T> responseEntity) {
		List<String> cookieHeaders = responseEntity.getHeaders().get(
				"Set-Cookie");
		if (CollectionUtils.isEmpty(cookieHeaders)) {
			return;
		}
		Map<String, Map<String, String>> cookies = parseCookies(cookieHeaders);
		Map<String, String> lTokenCookie = cookies.get(L_TOKEN_NAME);
		if (CollectionUtils.isEmpty(lTokenCookie)) {
			return;
		}
		String lToken = lTokenCookie.get(L_TOKEN_NAME);
		UserCache.setlToken(lToken);
		clearPersistLToken(context);
		new SharedPreferencesManager(context).commit(L_TOKEN_NAME, lToken);

	}
}
