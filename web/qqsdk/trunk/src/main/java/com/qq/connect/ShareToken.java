package com.qq.connect;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.qq.oauth.Config;
import com.qq.oauth.OAuth;
import com.qq.util.HttpClientUtils;
import com.qq.util.JackSonSerializer;

public class ShareToken extends Config {
	public ShareToken(String appKey, String appSecret) {
		super(appKey, appSecret);
	}

	// 参数顺序不能改改了会出现签名的错误。
	public Map<String, Object> addShare(String oauth_token,
			String oauth_token_secret, String openid, Map<String, String> shares)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException {

		String url = "http://openapi.qzone.qq.com/share/add_share";

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("format", "json"));
		if (shares.get("images") != null && shares.get("images").length() > 0) {
			parameters.add(new BasicNameValuePair("images", shares
					.get("images")));
		}

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
		if (shares.get("summary") != null && shares.get("summary").length() > 0) {
			parameters.add(new BasicNameValuePair("summary", shares
					.get("summary")));
		}
		parameters.add(new BasicNameValuePair("title", shares.get("title")));
		parameters.add(new BasicNameValuePair("url", shares.get("url")));

		parameters.add(new BasicNameValuePair(OAuth.OAUTH_SIGNATURE, OAuth
				.getOauthSignature("POST", url, parameters, oauth_token_secret,
						appSecret)));

		HttpPost sharePost = new HttpPost(url);
		sharePost.setHeader("Referer", "http://openapi.qzone.qq.com");
		sharePost.setHeader("Host", "openapi.qzone.qq.com");
		sharePost.setHeader("Accept-Language", "zh-cn");
		sharePost
				.setHeader("Content-Type", "application/x-www-form-urlencoded");
		sharePost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
		DefaultHttpClient httpclient = HttpClientUtils.getHttpClient();
		HttpResponse loginPostRes = httpclient.execute(sharePost);
		return JackSonSerializer.toMap(HttpClientUtils.getHtml(loginPostRes,
				"UTF-8", false));
	}
}
