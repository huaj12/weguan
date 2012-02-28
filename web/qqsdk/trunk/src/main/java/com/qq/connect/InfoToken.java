package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.qq.oauth.Config;
import com.qq.oauth.OAuth;
import com.qq.util.HttpClientUtils;
import com.qq.util.JackSonSerializer;
import com.qq.util.ParseString;

public class InfoToken extends Config {
	public InfoToken(String appKey, String appSecret) {
		super(appKey, appSecret);
	}

	public Map<String, String> getInfo(String oauth_token,
			String oauth_token_secret, String openid) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException {
		String url = "http://openapi.qzone.qq.com/user/get_user_info";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("format", "json"));
		parameters
				.add(new BasicNameValuePair(OAuth.OAUTH_CONSUMER_KEY, appKey));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_NONCE, OAuth
				.getOauthNonce()));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_SIGNATURE_METHOD,
				OAuth.OAUTH_SIGNATURE_METHOD_VALUE));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_TIMESTAMP, OAuth
				.getOauthTimestamp()));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_TOKEN, oauth_token));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_VERSION,
				OAuth.OAUTH_VERSION_VALUE));
		parameters.add(new BasicNameValuePair(OAuth.OPENID, openid));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_SIGNATURE, OAuth
				.getOauthSignature("GET", url, parameters, oauth_token_secret,
						appSecret)));

		url += "?" + OAuth.getSerialParameters(parameters, true);
		DefaultHttpClient httpclient = HttpClientUtils.getHttpClient();
		return JackSonSerializer.toMap(HttpClientUtils.getHtml(httpclient, url,
				"UTF-8"));
	}
}
