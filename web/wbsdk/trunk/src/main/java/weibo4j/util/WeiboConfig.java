package weibo4j.util;

import java.util.HashMap;
import java.util.Map;

public class WeiboConfig {
	private static Map<String, String> map = new HashMap<String, String>();

	public WeiboConfig() {
	}

	static {
		map.put("baseURL", "https://api.weibo.com/2/");
		map.put("accessTokenURL", "https://api.weibo.com/2/oauth2/access_token");
		map.put("authorizeURL", "https://api.weibo.com/2/oauth2/authorize");
	}

	public static String getValue(String key) {
		return map.get(key);
	}

	public static void updateProperties(String key, String value) {
		map.put(key, value);
	}

}
