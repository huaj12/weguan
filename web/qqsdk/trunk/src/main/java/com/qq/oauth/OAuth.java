package com.qq.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;

import com.qq.util.Base64Encoder;

public final class OAuth {

	public static final String OAUTH_VERSION = "oauth_version";
	public static final String OAUTH_VERSION_VALUE = "1.0";
	public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	public static final String OAUTH_NONCE = "oauth_nonce";
	public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
	public static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
	public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
	public static final String OAUTH_SIGNATURE = "oauth_signature";
	public static final String OAUTH_TOKEN = "oauth_token";
	public static final String OAUTH_VERICODE = "oauth_vericode";
	public static final String OPENID = "openid";
	public static final int THOUSAND = 1000;

	private static OAuth oauth = new OAuth();

	private OAuth() {
	}

	public static OAuth getInstance() {
		return oauth;
	}

	public static String getOauthNonce() {
		String str = Math.random() + "";
		return str.replaceFirst("^\\d.", "");
	}

	public static String getOauthTimestamp() {
		String str = new Date().getTime() / THOUSAND + "";
		return str;
	}

	public static String getOauthSignature(String method, String url,
			List<NameValuePair> parameters, String oauth_token_secret,
			String appSecret) throws UnsupportedEncodingException,
			InvalidKeyException, NoSuchAlgorithmException {
		String stepA1 = method;
		String stepA2 = URLEncoder.encode(url, "UTF-8");
		String stepA3 = getSerialParameters(parameters, false);
		String stepA = stepA1 + "&" + stepA2 + "&" + stepA3;
		String stepB = appSecret + "&" + oauth_token_secret;
		return getBase64Mac(stepA, stepB);
	}

	public static String getSerialParameters(List<NameValuePair> parameters,
			boolean onlySerialValue) throws UnsupportedEncodingException {
		String str = "";
		for (int i = 0; i < parameters.size(); i++) {
			NameValuePair item = parameters.get(i);
			if (onlySerialValue) {
				str += item.getName() + "="
						+ URLEncoder.encode(item.getValue(), "UTF-8");

			} else {
				str += item.getName() + "=" + item.getValue();
			}
			if (i < parameters.size() - 1) {
				str += "&";
			}
		}
		if (!onlySerialValue) {
			str = URLEncoder.encode(str, "UTF-8");
		}
		return str;
	}

	public static String getBase64Mac(String stepA, String stepB)
			throws NoSuchAlgorithmException, UnsupportedEncodingException,
			InvalidKeyException {
		byte[] oauthSignature = null;
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec spec = new SecretKeySpec(stepB.getBytes("US-ASCII"),
				"HmacSHA1");
		mac.init(spec);
		oauthSignature = mac.doFinal(stepA.getBytes("US-ASCII"));
		return Base64Encoder.encode(oauthSignature);
	}

}
