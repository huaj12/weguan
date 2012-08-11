package com.qq.oauth2;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qq.json.JSONException;
import com.qq.json.JSONObject;

public class Oauth extends QQ {
	private final Log log = LogFactory.getLog(Oauth.class);
	private static final long serialVersionUID = 718477295941786259L;

	public Oauth(String appkey, String sercret, String redirectUri) {
		super(appkey, sercret, redirectUri);
	}

	public String authorize(String display, String state) {
		StringBuilder qqLoginUrl = new StringBuilder();
		qqLoginUrl.append(baseURL + "oauth2.0/authorize");
		qqLoginUrl.append("?response_type=code");
		qqLoginUrl.append("&client_id=" + getAppkey());
		qqLoginUrl.append("&redirect_uri=" + getRedirectUri());
		// 请求用户授权时向用户显示的可进行授权的列表。如果要填写多个接口名称，请用逗号隔开。
		qqLoginUrl.append("&state=" + state);
		qqLoginUrl.append("&scope=" + "get_user_info,add_share");
		// 用于展示的样式。不传则默认展示为为PC下的样式。
		// 如果传入“mobile”，则展示为mobile端下的样式。
		qqLoginUrl.append("&display=" + display);
		return qqLoginUrl.toString();
	}

	/**
	 * 获取accessToken
	 * 
	 * @param authorizationCode
	 * @return
	 * @throws IOException
	 */
	public String getAccessToken(String authorizationCode, String state)
			throws IOException {
		StringBuilder accessTokenUrl = new StringBuilder();
		accessTokenUrl.append(baseURL + "oauth2.0/token");
		accessTokenUrl.append("?grant_type=authorization_code");
		accessTokenUrl.append("&client_id=" + getAppkey());
		accessTokenUrl.append("&client_secret=" + getSercret());
		accessTokenUrl.append("&code=" + authorizationCode);
		accessTokenUrl.append("&state=" + state);
		// 成功授权后的回调地址，建议设置为网站首页或网站的用户中心。
		accessTokenUrl.append("&redirect_uri=" + getRedirectUri());
		String result = get(accessTokenUrl.toString());
		String accessToken = null;
		if (result.indexOf("access_token") >= 0) {
			accessToken = result.split("&")[0].split("=")[1];
			return accessToken;
		}
		log.error("access_token result not find access_token");
		return accessToken;
	}

	public String getOpenId(String accessToken) throws IOException,
			JSONException {
		StringBuilder openIdUrl = new StringBuilder();
		openIdUrl.append(baseURL + "oauth2.0/me");
		openIdUrl.append("?access_token=" + accessToken);
		String result = get(openIdUrl.toString());
		JSONObject jsonObjRoot;
		String openId = null;
		String jsonStr = result.substring(result.indexOf("{"),
				result.indexOf("}") + 1);
		jsonObjRoot = new JSONObject(jsonStr);
		openId = jsonObjRoot.get("openid").toString();
		return openId;
	}
}
