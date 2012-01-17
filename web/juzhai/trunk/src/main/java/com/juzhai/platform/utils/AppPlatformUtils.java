package com.juzhai.platform.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.core.Constants;
import com.juzhai.passport.bean.AuthInfo;

public class AppPlatformUtils {
	private static final Log log = LogFactory.getLog(AppPlatformUtils.class);
	private static HttpsX509TrustManager xtm = new HttpsX509TrustManager();
	private static HttpsHostnameVerifier hnv = new HttpsHostnameVerifier();

	// 64位编码
	public static String urlBase64Encode(String str) {
		String basestr = "";
		try {
			Base64 base64en = new Base64();
			byte[] bytes = base64en.encode(str.getBytes(Constants.UTF8));
			basestr = new String(bytes, Constants.UTF8);
		} catch (Exception e) {
			log.error("urlBase64Encode is error", e);
		}
		return basestr.replaceAll("\\+", "\\*").replaceAll("/", "-")
				.replaceAll("=", "");
	}

	// 64位解码
	public static String urlBase64Decode(String str) {
		String result = "";
		try {
			str = str.replaceAll("\\*", "\\+").replaceAll("-", "/");
			Base64 base64en = new Base64();
			byte[] bytes = base64en.decode(str.getBytes(Constants.UTF8));
			result = new String(bytes, Constants.UTF8);
		} catch (Exception e) {
			log.error("urlBase64Decode is error", e);
		}
		return result;
	}

	/****
	 * 生成请求ＡＰＩ的ＵＲＬ参数
	 * 
	 * @param paramMap
	 * @return String
	 */
	public static String buildQuery(Map<String, String> paramMap,
			String api_key, String secret, String sessionKey, String v) {
		Map<String, String> param = new HashMap<String, String>();
		param.putAll(paramMap);
		param.put("api_key", api_key);
		param.put("call_id", String.valueOf(System.currentTimeMillis()));
		param.put("session_key", sessionKey);
		param.put("v", v);

		String request_str = "";
		Object[] paramKeys = param.keySet().toArray();
		Object[] paramKeys2 = new Object[paramKeys.length + 1];
		Arrays.sort(paramKeys);
		for (int i = 0; i < paramKeys.length; i++) {
			request_str += paramKeys[i] + "=" + param.get(paramKeys[i]);
			paramKeys2[i] = paramKeys[i];
		}
		String sig = request_str + secret;
		sig = DigestUtils.md5Hex(sig);

		paramKeys2[paramKeys.length] = "sig";
		param.put("sig", sig);
		String query = httpBuildQuery(param, paramKeys2);
		return query;
	}

	/****
	 * 生成请求开心ＡＰＩ的ＵＲＬ参数
	 * 
	 * @param paramMap
	 * @return String
	 */
	public static String kaiXinBuildQuery(Map<String, String> paramMap,
			AuthInfo authInfo, String v) {
		Map<String, String> param = new HashMap<String, String>();
		param.putAll(paramMap);
		param.put("oauth_consumer_key", authInfo.getAppKey());
		param.put("oauth_signature_method", "HMAC-SHA1");
		param.put("oauth_timestamp", String.valueOf(System.currentTimeMillis()));
		param.put("session_key", authInfo.getSessionKey());
		param.put("oauth_nonce", UUID.randomUUID().toString().replace("-", ""));
		param.put("oauth_version", v);

		String request_str = "";
		Object[] paramKeys = param.keySet().toArray();
		Object[] paramKeys2 = new Object[paramKeys.length + 1];
		Arrays.sort(paramKeys);
		for (int i = 0; i < paramKeys.length; i++) {
			request_str += paramKeys[i] + "=" + param.get(paramKeys[i]);
			paramKeys2[i] = paramKeys[i];
		}
		String sig = request_str + authInfo.getAppSecret();
		sig = DigestUtils.md5Hex(sig);

		paramKeys2[paramKeys.length] = "oauth_signature";
		param.put("oauth_signature", sig);
		String query = httpBuildQuery(param, paramKeys2);
		return query;
	}

	public static String sessionKeyBuildQuery(Map<String, String> paramMap,
			String session_key) {
		Map<String, String> param = new HashMap<String, String>();
		param.putAll(paramMap);
		param.put("session_key", session_key);
		String query = httpBuildQuery(param, null);
		return query;
	}

	public static String httpBuildQuery(Map<String, String> map, Object[] keyset) {
		StringBuffer strbuf = new StringBuffer();
		try {
			if (keyset == null) {
				Set<Entry<String, String>> set = map.entrySet();
				Iterator<Entry<String, String>> it = set.iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					strbuf.append(
							URLEncoder.encode(entry.getKey().toString(),
									Constants.UTF8)).append("=");
					strbuf.append(
							URLEncoder.encode(entry.getValue().toString(),
									Constants.UTF8)).append("&");
				}
			} else {
				for (int i = 0; i < keyset.length; i++) {
					strbuf.append(
							URLEncoder.encode(keyset[i].toString(),
									Constants.UTF8)).append("=");
					strbuf.append(
							URLEncoder.encode(map.get(keyset[i]),
									Constants.UTF8)).append("&");
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("httpBuildQuery is error", e);
		}
		if (strbuf.length() > 0) {
			strbuf.deleteCharAt(strbuf.length() - 1);
		}
		return strbuf.toString();
	}

	private static HttpURLConnection sendPost(String reqUrl, String params) {
		HttpURLConnection urlConn = null;
		SSLContext sslContext = null;
		try {
			if (reqUrl.startsWith("https:")) {
				sslContext = SSLContext.getInstance("TLS");
				X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
				sslContext.init(null, xtmArray,
						new java.security.SecureRandom());
				if (sslContext != null) {
					HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
							.getSocketFactory());
				}
				HttpsURLConnection.setDefaultHostnameVerifier(hnv);
			}
			urlConn = (HttpURLConnection) ((new URL(reqUrl)).openConnection());
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(2000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			byte[] b = params.getBytes();
			urlConn.getOutputStream().write(b, 0, b.length);
			urlConn.getOutputStream().flush();
			urlConn.getOutputStream().close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return urlConn;
	}

	private static URLConnection sendSslGet(String url) {
		SSLContext sslContext = null;
		URLConnection urlCon = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
			X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
			sslContext.init(null, xtmArray, new java.security.SecureRandom());
			if (sslContext != null) {
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
						.getSocketFactory());
			}
			HttpsURLConnection.setDefaultHostnameVerifier(hnv);
			urlCon = (new URL(url)).openConnection();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return urlCon;
	}

	public static String getContent(URLConnection urlConn) {
		try {
			String responseContent = null;
			InputStream in = urlConn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					Constants.UTF8));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
			return responseContent;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return StringUtils.EMPTY;
	}

	public static String doPost(String reqUrl, String parameters) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendPost(reqUrl, parameters);
			String responseContent = getContent(urlConn);
			return responseContent.trim();
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
				urlConn = null;
			}
		}
	}

	public static String doSllGet(String reqUrl, String parameters) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = (HttpURLConnection) sendSslGet(reqUrl + "?" + parameters);
			String responseContent = getContent(urlConn);
			return responseContent.trim();
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
				urlConn = null;
			}
		}
	}

	/**
	 * 验证请求
	 * 
	 * @param query
	 * @return boolean
	 */
	public static boolean checkSignFromQuery(String query, String secret) {
		String[] querys = query.split("&sig=");
		String localsig = DigestUtils.md5Hex((querys[0] + secret));
		if (localsig.equals(querys[1])) {
			return true;
		} else {
			return false;
		}
	}

}

class HttpsX509TrustManager implements X509TrustManager {
	public HttpsX509TrustManager() {
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}

class HttpsHostnameVerifier implements HostnameVerifier {
	public HttpsHostnameVerifier() {
	}

	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
