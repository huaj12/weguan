/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.spider.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.junit.Test;

import com.juzhai.core.util.JackSonSerializer;

public class ProvinceAndCity {

	@Test
	public void kaixin() throws HttpException, IOException {
		for (Map<String, Object> provinceMap : getKaixinCityDoGet("type=0")) {
			System.out.print(provinceMap.get("name") + ":");
			Integer id = (Integer) provinceMap.get("id");
			for (Map<String, Object> cityMap : getKaixinCityDoGet("type=1&id="
					+ id)) {
				System.out.print(cityMap.get("name") + ",");
			}
			System.out.println();
		}
	}

	private Map<String, Object>[] getKaixinCityDoGet(String queryString)
			throws JsonGenerationException, URIException, HttpException,
			IOException {
		return JackSonSerializer.toMapArray(getCityDoGet(
				"http://www.kaixin001.com/interface/suggestlocation.php",
				queryString));
	}

	private String getCityDoGet(String url, String queryString)
			throws URIException, IOException, HttpException {
		String response = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		method.setQueryString(URIUtil.encodeQuery(queryString));
		client.executeMethod(method);
		if (method.getStatusCode() == HttpStatus.SC_OK) {
			response = method.getResponseBodyAsString();
		}
		method.releaseConnection();
		return response;
	}

	@Test
	public void renren() throws HttpException, IOException {
		String[] provinceArray = new String[] { "\u5317\u4eac", "\u4e0a\u6d77",
				"\u9ed1\u9f99\u6c5f", "\u5409\u6797", "\u8fbd\u5b81",
				"\u5929\u6d25", "\u5b89\u5fbd", "\u6c5f\u82cf", "\u6d59\u6c5f",
				"\u9655\u897f", "\u6e56\u5317", "\u5e7f\u4e1c", "\u6e56\u5357",
				"\u7518\u8083", "\u56db\u5ddd", "\u5c71\u4e1c", "\u798f\u5efa",
				"\u6cb3\u5357", "\u91cd\u5e86", "\u4e91\u5357", "\u6cb3\u5317",
				"\u6c5f\u897f", "\u5c71\u897f", "\u8d35\u5dde", "\u5e7f\u897f",
				"\u5185\u8499\u53e4", "\u5b81\u590f", "\u9752\u6d77",
				"\u65b0\u7586", "\u6d77\u5357", "\u897f\u85cf", "\u9999\u6e2f",
				"\u6fb3\u95e8", "\u53f0\u6e7e", "\u5176\u5b83\u56fd\u5bb6" };
		String body = getCityDoGet(
				"http://s.xnimg.cn/a13818/js/inCityArray.js", "");
		BufferedReader br = new BufferedReader(new StringReader(body));
		int i = 0;
		while (true) {
			String temp = br.readLine();
			if (StringUtils.isEmpty(temp) || provinceArray.length <= i) {
				break;
			}
			String province = StringEscapeUtils.unescapeJava(provinceArray[i]);
			System.out.print(province + ":");
			String line = StringEscapeUtils.unescapeJava(temp);
			String value = line.split("=")[1].trim();
			String[] cityArray = StringUtils.substring(value, 1,
					value.length() - 2).split(",");
			for (String city : cityArray) {
				city = city.split(":")[1];
				System.out.print(city.substring(0, city.length() - 1) + ",");
			}
			System.out.println();
			i++;
		}
	}
}
