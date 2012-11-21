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

	public static void updateUser(Context context, User user) {
		if (user != null) {
			SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(
					context);
			UserCacheManager.getUserCache(context).setUserInfo(user);
			long uid = user.getUid();
			if (uid > 0) {
				sharedPreferencesManager.commit(UID_NAME, String.valueOf(uid));
			}
			try {
				String jsonUserStr = JacksonSerializer.toString(user);
				sharedPreferencesManager.commit(USER_INFO, jsonUserStr);
			} catch (JsonGenerationException e) {
				if (BuildConfig.DEBUG) {
					Log.d("UserCacheManager",
							"updateUser JsonGenerationException", e);
				}
			}
		}

	}

	public static void updateTokens(Context context,
			Map<String, Map<String, String>> cookies) {
		SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(
				context);
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		if (!CollectionUtils.isEmpty(cookies.get(P_TOKEN_NAME))) {
			String pToken = cookies.get(P_TOKEN_NAME).get(P_TOKEN_NAME);
			if (StringUtils.hasText(pToken)) {
				applicationContext.getUserCache().setpToken(pToken);
				sharedPreferencesManager.commit(P_TOKEN_NAME, pToken);
			}

		}
		if (!CollectionUtils.isEmpty(cookies.get(L_TOKEN_NAME))) {
			String lToken = cookies.get(L_TOKEN_NAME).get(L_TOKEN_NAME);
			if (StringUtils.hasText(lToken)) {
				sharedPreferencesManager.commit(L_TOKEN_NAME, lToken);
				applicationContext.getUserCache().setlToken(lToken);
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
		CookieSyncManager.createInstance(context);
		CookieManager.getInstance().removeAllCookie();
		UserCacheManager.getUserCache(context).clear();
		clearPersistToken(context);
		clearPersistLToken(context);
		clearPersistUid(context);
		clearPersistUserInfo(context);
	}

	public static void localLogin(Context context,
			ResponseEntity<UserResult> responseEntity) {
		// 保存登录信息
		ApplicationContext applicationContext = (ApplicationContext) context
				.getApplicationContext();
		List<String> cookiesList = responseEntity.getHeaders()
				.get("Set-Cookie");
		List<String> cookieHeaders = new ArrayList<String>();
		if (null != cookiesList) {
			cookieHeaders.addAll(cookiesList);
		}
		Map<String, Map<String, String>> cookies = parseCookies(cookieHeaders);
		createToken(L_TOKEN_NAME, cookies, cookieHeaders, applicationContext);
		createToken(P_TOKEN_NAME, cookies, cookieHeaders, applicationContext);
		updateTokens(context, cookies);
		synCookieManager(applicationContext, cookieHeaders);
		UserCacheManager.updateUser(context, responseEntity.getBody()
				.getResult());
	}

	private static void synCookieManager(ApplicationContext context,
			List<String> cookieHeaders) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		if (null != cookieHeaders) {
			for (String cookie : cookieHeaders) {
				cookieManager.setCookie(context.getBaseUrl(), cookie);
			}
		}
		CookieSyncManager.getInstance().sync();
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
		updateTokens(context, cookies);
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
