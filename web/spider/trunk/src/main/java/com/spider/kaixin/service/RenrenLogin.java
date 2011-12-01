package com.spider.kaixin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class RenrenLogin {

	// The configuration items
	private static String userName = "10019922@qq.com";
	private static String password = "xunyang1987";
	private static String redirectURL = "http://apps.renren.com/juzhaiqi?origin=103";

	// Don't change the following URL
	private static String renRenLoginURL = "http://www.renren.com/PLogin.do";

	// The HttpClient is used in one session
	private HttpResponse response;
	private DefaultHttpClient httpclient = new DefaultHttpClient();

	private boolean login() {
		HttpPost httpost = new HttpPost(renRenLoginURL);
		// All the parameters post to the web site
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("origURL", redirectURL));
		nvps.add(new BasicNameValuePair("domain", "renren.com"));
		nvps.add(new BasicNameValuePair("isplogin", "true"));
		nvps.add(new BasicNameValuePair("formName", ""));
		nvps.add(new BasicNameValuePair("method", ""));
		nvps.add(new BasicNameValuePair("submit", "登录"));
		nvps.add(new BasicNameValuePair("email", userName));
		nvps.add(new BasicNameValuePair("password", password));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httpost);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			httpost.abort();
		}
		return true;
	}

	private String getRedirectLocation() {
		Header locationHeader = response.getFirstHeader("Location");
		if (locationHeader == null) {
			return null;
		}
		return locationHeader.getValue();
	}

	private String getText(String redirectLocation) {
		HttpGet httpget = new HttpGet(redirectLocation);
		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = "";
		try {
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
			responseBody = null;
		} finally {
			httpget.abort();
		}
		return responseBody;
	}
	
	public  void getData(String juzhaiqiUrl) {
		juzhaiqiUrl="http://rr.51juzhai.com/?origin=103&amp;xn_sig_in_iframe=1&amp;xn_sig_method=get&amp;xn_sig_time=1322707272010&amp;xn_sig_user=745223738&amp;xn_sig_expires=1322712000&amp;xn_sig_session_key=2.ad890162320041f1778e200170cb6fdc.3600.1322712000-745223738&amp;xn_sig_added=1&amp;xn_sig_ext_perm=publish_feed&amp;xn_sig_api_key=ec44a955931e42519c411ae385a55aa6&amp;xn_sig_app_id=163941&amp;xn_sig_domain=renren.com&amp;xn_sig_user_src=kx&amp;xn_sig=be649faf324f2564a5b4d767ef045678";
		HttpGet httpget = new HttpGet(juzhaiqiUrl.replaceAll("&amp;", "&"));
		String data="";
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			data = httpclient.execute(httpget,responseHandler);
			int begin=data.indexOf("data={")+6;
			int end=data.indexOf("}", begin);
			data=data.substring(begin, end);
			data=data.replaceAll("\"", "");
			data=data.replaceAll(",", "&");
			data=data.replaceAll(":", "=");
			System.out.println(data);
		}catch (Exception e) {
			e.printStackTrace();
		}  finally {
			httpget.abort();
		}
		updateSession(data);
	}
	
	public  void updateSession(String data) {
		HttpGet accessget = new HttpGet("http://www.51juzhai.com/access?"+data);
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String success = httpclient.execute(accessget,responseHandler);
			System.out.println(success);
		}catch (Exception e) {
			e.printStackTrace();
		}  finally {
			accessget.abort();
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	
	

	public void printText() {
		if (login()) {
			String redirectLocation = getRedirectLocation();
			if (redirectLocation != null) {
				String responseBody=getText(redirectLocation);
				int begin = responseBody.indexOf("http://rr.51juzhai.com/");
				int end = responseBody.indexOf("\" frameborder");
				System.out.println(begin+":"+end);
				responseBody = responseBody.substring(begin, end);
				getData(responseBody);
			}
		}
	}

	public static void main(String[] args) {
		RenrenLogin renRen = new RenrenLogin();
		renRen.printText();
	}

}
