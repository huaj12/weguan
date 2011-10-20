package com.spider.kaixin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;

import com.spider.kaixin.utils.KaiXinJsUtils;

public class KaiXinLogin {

	private String url = "http://www.kaixin001.com/!app_juzhaiqi/";
	private String domian = "http://www.kaixin001.com";
	private String kaiXinLoginURL = "http://www.kaixin001.com/login/login_api.php";
	private DefaultHttpClient httpclient = new DefaultHttpClient();

	private String getEncryptKey(String userName) {
		HttpPost httpost = new HttpPost(kaiXinLoginURL);

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("email", userName));

		String encryptKey = "";
		try {
			httpost.setEntity(new UrlEncodedFormEntity(formparams, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
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

	public void login(String userName,String password) throws Exception{
		HttpPost httpost = new HttpPost(kaiXinLoginURL);

		String encryptKey = getEncryptKey(userName);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("ver", "1"));
		nvps.add(new BasicNameValuePair("email", userName));
		nvps.add(new BasicNameValuePair("rpasswd",
				getEncryptPassword(encryptKey,password)));
		nvps.add(new BasicNameValuePair("encypt", encryptKey));
		nvps.add(new BasicNameValuePair("url", "/home/"));
//		nvps.add(new BasicNameValuePair("remember", "1"));
		HttpResponse response=null;
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response  = httpclient.execute(httpost);
		}  finally {
			httpost.abort();
		}
		getJuZhaiUrl(response);
	}

	public void getJuZhaiUrl(HttpResponse response) throws Exception {
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
		} finally {
			httpget.abort();
		}
		updateSession(responseBody,response);
	}

	public  void updateSession(String juzhaiqiUrl,HttpResponse response) throws ClientProtocolException, IOException {
		if(juzhaiqiUrl==null){
			System.out.println("登陆失败");
			return ;
		}
		HttpGet httpget = new HttpGet(domian + juzhaiqiUrl);
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String body = httpclient.execute(httpget,responseHandler);
			int begin=body.indexOf("data={")+6;
			int end=body.indexOf("}", begin);
			body=body.substring(begin, end);
			body=body.replaceAll("\"", "");
			body=body.replaceAll(",", "&");
			body=body.replaceAll(":", "=");
			System.out.println(body);
			HttpGet accessget = new HttpGet("http://kx.51juzhai.com/access?"+body);
			response = httpclient.execute(accessget);
		}  finally {
			httpget.abort();
		}
	}
	
	public static void  start(String userName,String password){
		try{
		KaiXinLogin kaixin=new KaiXinLogin();
		kaixin.login(userName,password);
		}catch (Exception e) {
			System.out.println(userName+":"+password+"  login is error"+e);
		}
		
	}

	public static void main(String[] args) {
//		
	}

}
