/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qplus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author nbzhang
 */
public class QOpenService extends QPlusService {

	public static final String VERSION = "1.0.1";

	private int applang = 2052;

	/**
	 * 
	 * @param appid
	 *            申请的应用ID
	 * @param appsecret
	 *            应用密钥，用于验证合法性
	 * @return
	 */
	public static final QOpenService createInstance(int appid, String appsecret) {
		return createInstance(appid, appsecret, 2052);
	}

	/**
	 * 
	 * @param appid
	 *            申请的应用ID
	 * @param appsecret
	 *            应用密钥，用于验证合法性
	 * @param applang
	 *            语言版本
	 * @return
	 */
	public static final QOpenService createInstance(int appid,
			String appsecret, int applang) {
		return new QOpenService(appid, appsecret, applang);
	}

	public String getLoginParams(final QOpenBean bean) throws IOException {
		String param1 = "app_userip=" + bean.getUseripaddr();
		String param2 = "app_lang=" + this.applang;
		return createQPlusParams("app_qqlogin", null, param1, param2);
	}

	private QOpenService(int appid, String appsecret, int applang) {
		super(appid, appsecret + "&");
		this.applang = applang;
	}

	public boolean checkSig(String url) throws IOException {
		if (url == null || url.indexOf('?') < 0 || url.indexOf("sig=") < 0)
			return false;
		url = url.substring(url.indexOf('?') + 1);
		String sig = url.substring(url.indexOf("sig=") + "sig=".length());
		if (sig.indexOf('&') > 0)
			sig = sig.substring(0, sig.indexOf('&'));
		return sig.equalsIgnoreCase(createSigValue(url.replaceFirst(
				"(^sig=[^&]+&)|(&sig=[^&]+)", "")));
	}

	public boolean checkParamsSig(String url) {
		if (url == null || url.indexOf("sig=") < 0)
			return false;
		String sig = url.substring(url.indexOf("sig=") + "sig=".length());
		if (sig.indexOf('&') > 0)
			sig = sig.substring(0, sig.indexOf('&'));
		return sig.equalsIgnoreCase(createSigValue(url.replaceFirst(
				"(^sig=[^&]+&)|(&sig=[^&]+)", "")));
	}

	public QOpenResult checkLogin(final QOpenBean bean) throws IOException {
		return send("app_verify", bean);
	}

	public QOpenResult logOut(final QOpenBean bean) throws IOException {
		return send("app_logout", bean);
	}

	public QOpenResult getUserInfo(final QOpenBean bean) throws IOException {
		return send("app_get_userinfo", bean);
	}

	private String createQPlusParams(final String action, final QOpenBean bean,
			final String... params) throws IOException {
		List<String> list = new ArrayList<String>(8 + params.length);
		if (bean != null) {
			list.add("app_openid=" + bean.getAppopenid());
			list.add("app_openkey=" + bean.getAppopenkey());
			list.add("app_userip=" + bean.getUseripaddr());
		}
		list.add("app_id=" + this.appid);
		list.add("app_ts=" + intNow());
		list.add("app_nonce=" + random());
		for (String param : params) {
			list.add(param);
		}
		String str = createHttpParam(list);
		return str + "&sig=" + createSigValue(action + "&" + str);
	}

	private QOpenResult send(final String action, final QOpenBean bean,
			final String... params) throws IOException {
		final String url = "http://openid.qplus.com/cgi-bin/" + action;
		final String paramstr = createQPlusParams(action, bean, params);
		String content = send(url, paramstr);
		return new QOpenResult(content);
	}

	public QOpenResult feed(Map<String, String> shares) throws IOException {
		final String url = "https://openapi.qplus.com/openapi/share/add_qplus_feeds";
		List<String> list = new ArrayList<String>(shares.size());
		for (Entry<String, String> entry : shares.entrySet()) {
			list.add(entry.getKey() + "=" + entry.getValue());
		}
		String paramstr = createHttpParam(list);
		String content = send(url, paramstr);
		return new QOpenResult(content);
	}
}
