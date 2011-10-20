package com.spider.kaixin;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.spider.kaixin.utils.KaiXinJsUtils;

public class KaiXinLogin {

	private String url = "http://www.kaixin001.com/!app_juzhaiqi/";
	private String domian = "http://www.kaixin001.com";
	private String kaiXinLoginURL = "http://www.kaixin001.com/login/login_api.php";
	private HttpResponse response;
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private static KaiXinLogin kaixin=new KaiXinLogin();

	private String getEncryptKey(String userName) {
		HttpPost httpost = new HttpPost(kaiXinLoginURL);

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("email", userName));

		String encryptKey = "";
		try {
			httpost.setEntity(new UrlEncodedFormEntity(formparams, HTTP.UTF_8));
			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String responseBody = EntityUtils.toString(entity);
				if (responseBody.length() > 0) {
					String[] s = responseBody.split(":");
					encryptKey = s[1].replace("\"", "").replace("}", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptKey;
	}

	private String getEncryptPassword(String encryptKey,String password) {
		return KaiXinJsUtils.getPassword(password, encryptKey);
	}

	public boolean login(String userName,String password) {
		HttpPost httpost = new HttpPost(kaiXinLoginURL);

		String encryptKey = getEncryptKey(userName);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("ver", "1"));
		nvps.add(new BasicNameValuePair("email", userName));
		nvps.add(new BasicNameValuePair("rpasswd",
				getEncryptPassword(encryptKey,password)));
		nvps.add(new BasicNameValuePair("encypt", encryptKey));
		nvps.add(new BasicNameValuePair("url", "/home/"));
		nvps.add(new BasicNameValuePair("remember", "1"));
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

	public String getJuZhaiUrl() {
		HttpGet httpget = new HttpGet(url);

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = "";
		try {
			responseHandler.handleResponse(response);
			responseBody = httpclient.execute(httpget, responseHandler);
			response = httpclient.execute(httpget);
			int begin = responseBody.indexOf("/app/restapp.php?");
			int end = responseBody.indexOf("\"></iframe");
			responseBody = responseBody.substring(begin, end);
		} catch (Exception e) {
			responseBody = null;
		} finally {
			httpget.abort();
		}
		return responseBody;
	}

	public  void updateSession(String juzhaiqiUrl) {
		if(juzhaiqiUrl==null)
			return ;
		HttpGet httpget = new HttpGet(domian + juzhaiqiUrl);
		try {
			response = httpclient.execute(httpget);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpget.abort();
		}
	}
	
	public static void  start(String userName,String password){
		if(kaixin.login(userName,password)){
			kaixin.updateSession(kaixin.getJuZhaiUrl());
		}else{
			System.out.println(userName+":"+password+"  login is error");
		}
	}

	public static void main(String[] args) {
//		
	}

}
