package com.juzhai.android.passport.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.juzhai.android.BuildConfig;
import com.juzhai.android.core.ApplicationContext;
import com.juzhai.android.core.data.SharedPreferencesManager;
import com.juzhai.android.core.model.Result.UserResult;
import com.juzhai.android.core.utils.JacksonSerializer;
import com.juzhai.android.passport.model.User;

public class UserCacheManager {

	private final static String P_TOKEN_NAME = "p_token";
	private final static String L_TOKEN_NAME = "l_token";
	private final static String UID_NAME = "uid";
	private final static String USER_INFO = "user_info";
	private final static String SEMICOLON = ";";
	private final static String EQUALSIGN = "=";

	public static void cache(Context context,
			ResponseEntity<UserResult> responseEntity) {
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		applicationContext.getUserCache().setUserInfo(
				responseEntity.getBody().getResult());
		List<String> cookiesList = responseEntity.getHeaders()
				.get("Set-Cookie");
		List<String> cookieHeaders = new ArrayList<String>();
		if (null != cookiesList) {
			cookieHeaders.addAll(cookiesList);
		}
		Map<String, Map<String, String>> cookies = parseCookies(cookieHeaders);
		createToken(L_TOKEN_NAME, cookies, cookieHeaders, applicationContext);
		createToken(P_TOKEN_NAME, cookies, cookieHeaders, applicationContext);
		Map<String, String> pTokenMap = cookies.get(P_TOKEN_NAME);
		Map<String, String> lTokenMap = cookies.get(L_TOKEN_NAME);
		if (lTokenMap != null) {
			String lToken = lTokenMap.get(L_TOKEN_NAME);
			if (StringUtils.hasText(lToken)) {
				applicationContext.getUserCache().setlToken(lToken);
			}
		}
		if (pTokenMap != null) {
			String pToken = pTokenMap.get(P_TOKEN_NAME);
			if (StringUtils.hasText(pToken)) {
				applicationContext.getUserCache().setpToken(pToken);
			}
		}

		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		// cookieManager.removeSessionCookie();
		if (null != cookieHeaders) {
			for (String cookie : cookieHeaders) {
				cookieManager
						.setCookie(applicationContext.getBaseUrl(), cookie);
			}
		}
		CookieSyncManager.getInstance().sync();
	}

	public static void updateUserCache(Context context, User user) {
		UserCacheManager.getUserCache(context).setUserInfo(user);
	}

	public static void persistInfo(Context context,
			ResponseEntity<UserResult> responseEntity) {
		SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(
				context);
		Map<String, Map<String, String>> cookies = parseCookies(responseEntity
				.getHeaders().get("Set-Cookie"));
		if (cookies.get(P_TOKEN_NAME) != null) {
			String pToken = cookies.get(P_TOKEN_NAME).get(P_TOKEN_NAME);
			sharedPreferencesManager.commit(P_TOKEN_NAME, pToken);
		}
		if (cookies.get(L_TOKEN_NAME) != null) {
			String lToken = cookies.get(L_TOKEN_NAME).get(L_TOKEN_NAME);
			sharedPreferencesManager.commit(L_TOKEN_NAME, lToken);
		}
		User user = responseEntity.getBody().getResult();
		if (user != null) {
			long uid = responseEntity.getBody().getResult().getUid();
			if (uid > 0) {
				sharedPreferencesManager.commit(UID_NAME, String.valueOf(uid));
			}
			try {
				String jsonUserStr = JacksonSerializer.toString(user);
				sharedPreferencesManager.delete(USER_INFO);
				sharedPreferencesManager.commit(USER_INFO, jsonUserStr);
			} catch (JsonGenerationException e) {
				if (BuildConfig.DEBUG) {
					Log.d("UserCacheManager",
							"persistInfo JsonGenerationException", e);
				}
			}
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

	public static User getPersistUserInfo(Context context) {
		String str = new SharedPreferencesManager(context).getString(USER_INFO);
		User user = null;
		try {
			user = new ObjectMapper().readValue(str, User.class);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) {
				Log.d("getPersistUserInfo", "json to user is error", e);
			}
		}
		return user;
	}

	public static void clearPersistUserInfo(Context context) {
		new SharedPreferencesManager(context).delete(USER_INFO);
	}

	public static void localLogout(Context context) {
		UserCacheManager.getUserCache(context).clear();
		clearPersistToken(context);
		clearPersistLToken(context);
		clearPersistUid(context);
		clearPersistUserInfo(context);
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

	private static String newTokenString(String name, String value,
			ApplicationContext config) {
		if (StringUtils.hasText(value)) {
			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append(EQUALSIGN);
			sb.append(value);
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
		UserCacheManager.getUserCache(context).setlToken(lToken);
		new SharedPreferencesManager(context).commit(L_TOKEN_NAME, lToken);
	}

	public static UserCache getUserCache(Context context) {
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		UserCache cache = applicationContext.getUserCache();
		if (cache.getUserInfo() == null) {
			cache.setUserInfo(getPersistUserInfo(context));
		}
		if (cache.getlToken() == null) {
			cache.setlToken(getPersistLToken(context));
		}
		if (cache.getpToken() == null) {
			cache.setpToken(getPersistToken(context));
		}
		return applicationContext.getUserCache();
	}

	public static void createToken(String name,
			Map<String, Map<String, String>> cookies,
			List<String> cookieHeaders, ApplicationContext applicationContext) {
		if (!cookies.containsKey(name)) {
			String tokenString = newTokenString(name,
					new SharedPreferencesManager(applicationContext)
							.getString(name), applicationContext);
			if (StringUtils.hasText(tokenString)) {
				cookieHeaders.add(tokenString);
				putParseCookie(cookies, tokenString);
			}
		}
	}
}
