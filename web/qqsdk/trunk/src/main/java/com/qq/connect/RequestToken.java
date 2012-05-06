package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.qq.oauth.Config;
import com.qq.oauth.OAuth;
import com.qq.util.HttpClientUtils;
import com.qq.util.ParseString;

public class RequestToken extends Config {

	public RequestToken(String appKey, String appSecret) {
		super(appKey, appSecret);
	}

	public Map<String, String> getRequestToken() throws IOException,
			InvalidKeyException, NoSuchAlgorithmException {

		String url = "http://openapi.qzone.qq.com/oauth/qzoneoauth_request_token";

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters
				.add(new BasicNameValuePair(OAuth.OAUTH_CONSUMER_KEY, appKey));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_NONCE, OAuth
				.getOauthNonce()));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_SIGNATURE_METHOD,
				OAuth.OAUTH_SIGNATURE_METHOD_VALUE));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_TIMESTAMP, OAuth
				.getOauthTimestamp()));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_VERSION,
				OAuth.OAUTH_VERSION_VALUE));
		parameters.add(new BasicNameValuePair(OAuth.OAUTH_SIGNATURE, OAuth
				.getOauthSignature("GET", url, parameters, "", appSecret)));
		url += "?" + OAuth.getSerialParameters(parameters, true);
		DefaultHttpClient httpclient = HttpClientUtils.getHttpClient();
		String requestToken = HttpClientUtils.getHtml(httpclient, url, "UTF-8");
		return ParseString.parseTokenString(requestToken);
	}

}
