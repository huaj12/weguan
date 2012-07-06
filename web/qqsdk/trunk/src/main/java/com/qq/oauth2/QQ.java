package com.qq.oauth2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.qq.util.HttpClientUtils;

public class QQ implements java.io.Serializable {

	private static final long serialVersionUID = 1644002543341237626L;
	private String token;
	private String appkey;
	private String sercret;
	private String redirectUri;

	protected String baseURL = "https://graph.qq.com/";

	public QQ(String appkey, String sercret, String redirectUri) {
		this.appkey = appkey;
		this.sercret = sercret;
		this.redirectUri = redirectUri;
	}

	public QQ(String token, String appkey, String sercret, String redirectUri) {
		this.token = token;
		this.appkey = appkey;
		this.sercret = sercret;
		this.redirectUri = redirectUri;
	}

	protected DefaultHttpClient client = HttpClientUtils.getHttpClient();

	public String getAppkey() {
		return appkey;
	}

	public String getSercret() {
		return sercret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public String getToken() {
		return token;
	}

	protected String get(String url) throws IOException {
		HttpGet get = new HttpGet(url);
		HttpResponse res = client.execute(get);
		String result = HttpClientUtils.getHtml(res, "UTF-8", false);
		return result;
	}

}
