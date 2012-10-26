package com.juzhai.android.passport.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

	public static void cache(Context context,
			ResponseEntity<UserResult> responseEntity) {
		SystemConfig config = (SystemConfig) context.getApplicationContext();
		UserCache.setUserInfo(responseEntity.getBody().getResult());
		List<String> cookieHeaders = responseEntity.getHeaders().get(
				"Set-Cookie");
		Map<String, Map<String, String>> cookies = parseCookies(cookieHeaders);
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

	public static void persistToken(Context context,
			ResponseEntity<UserResult> responseEntity) {
		Map<String, Map<String, String>> cookies = parseCookies(responseEntity
				.getHeaders().get("Set-Cookie"));
		if (cookies.get(P_TOKEN_NAME) != null) {
			String pToken = cookies.get(P_TOKEN_NAME).get(P_TOKEN_NAME);
			new SharedPreferencesManager(context).commit(P_TOKEN_NAME, pToken);
		}
	}

	public static String getPersistToken(Context context) {
		return new SharedPreferencesManager(context).getString(P_TOKEN_NAME);
	}

	public static void clearPersistToken(Context context) {
		new SharedPreferencesManager(context).delete(P_TOKEN_NAME);
	}

	public static void localLogout(Context context) {
		UserCache.clear();
		clearPersistToken(context);
	}

	private static Map<String, Map<String, String>> parseCookies(
			List<String> list) {
		Map<String, Map<String, String>> cookies = new HashMap<String, Map<String, String>>();
		if (null != list) {
			for (String str : list) {
				String[] cookiesStr = str.split(";");
				Map<String, String> map = new HashMap<String, String>();
				String key = null;
				for (String cookieStr : cookiesStr) {
					String[] values = cookieStr.split("=");
					if (values != null && values.length > 1) {
						if (null == key) {
							key = values[0];
						}
						map.put(values[0], values[1]);
					}
				}
				cookies.put(key, map);
			}
		}
		return cookies;
	}
}
