package com.easylife.movie.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.easylife.movie.core.ApplicationContext;

import com.easylife.movie.core.ApplicationContext;

public class HttpUtils {
	private static int CONNECT_TIMEOUT = 20000;
	private static int READ_TIMEOUT = 30000;
	private static BasicHttpParams httpParams = null;

	public static String getContent(String url) {
		if (httpParams == null) {
			httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
		}
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet accessget = new HttpGet(url);
		String content = "";
		try {
			content = httpclient.execute(accessget, responseHandler);
		} catch (Exception e) {
			Log.e("weather_spider", e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return toUtf8String(content);
	}

	public static String toUtf8String(String s) {
		try {
			return new String(s.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static <T> ResponseEntity<T> get(Context context, String uri,
			Map<String, Object> values, Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(
				requestHeaders);
		RestTemplate restTemplate = createRestTemplate(context);
		if (null == restTemplate) {
			throw new RestClientException("no network");
		}
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		ApplicationContext config = (ApplicationContext) context
				.getApplicationContext();
		ResponseEntity<T> responseEntity = restTemplate.exchange(
				config.getBaseUrl() + createHttpParam(uri, values),
				HttpMethod.GET, requestEntity, responseType);
		return responseEntity;
	}

	private static String createHttpParam(String uri, Map<String, Object> values) {
		if (CollectionUtils.isEmpty(values)) {
			return uri;
		}
		StringBuilder str = new StringBuilder();
		if (uri.indexOf("?") == -1) {
			str.append("?");
		}
		for (Entry<String, Object> entry : values.entrySet()) {
			if (str.length() != 1) {
				str.append('&');
			}
			str.append(entry.getKey());
			str.append('=');
			str.append(String.valueOf(entry.getValue()));
		}
		return uri + str.toString();
	}

	private static RestTemplate createRestTemplate(final Context context) {
		boolean hasNetwork = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager.getActiveNetworkInfo() != null) {
			hasNetwork = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		if (!hasNetwork) {
			return null;
		}
		SimpleClientHttpRequestFactory scrf = new SimpleClientHttpRequestFactory();
		scrf.setConnectTimeout(CONNECT_TIMEOUT);
		scrf.setReadTimeout(READ_TIMEOUT);
		RestTemplate restTemplate = new RestTemplate(scrf);
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response)
					throws IOException {
				if (null == response || response.getStatusCode() == null) {
					return false;
				}
				return !HttpStatus.OK.equals(response.getStatusCode());
			}

			@Override
			public void handleError(ClientHttpResponse response)
					throws IOException {
			}
		});
		return restTemplate;
	}

	public static <T> ResponseEntity<T> post(Context context, String uri,
			Map<String, Object> values, Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		requestHeaders.setContentType(new MediaType("application",
				"x-www-form-urlencoded"));
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (!CollectionUtils.isEmpty(values)) {
			for (Entry<String, Object> entry : values.entrySet()) {
				formData.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				formData, requestHeaders);
		RestTemplate restTemplate = createRestTemplate(context);
		if (null == restTemplate) {
			throw new RestClientException("no network");
		}
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		ApplicationContext config = (ApplicationContext) context
				.getApplicationContext();
		ResponseEntity<T> responseEntity = restTemplate.exchange(
				config.getBaseUrl() + uri, HttpMethod.POST, requestEntity,
				responseType);
		return responseEntity;
	}
}
