package com.spider.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.spider.core.service.IBaseService;

public class BaseService implements IBaseService {
	private DefaultHttpClient httpclient = new DefaultHttpClient();

	private String getContentByUrl(String url, String regEx) {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet accessget = new HttpGet(url);
		String content = "";
		try {
			content = httpclient.execute(accessget, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	private List<String> find(String content, String regEx) {
		List<String> list = new ArrayList<String>();
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(content);
		while (mat.find()) {
			list.add(mat.group(1));
		}
		return list;
	}

	public String findContent(String url, String regEx) {
		String content=getContentByUrl(url, regEx);
		List<String> list=find(content, regEx);
		return list.size()>0?list.get(0):null;
	}

	public List<String> findContents(String url, String regEx) {
		String content=getContentByUrl(url, regEx);
		return find(content, regEx);
	}
}
