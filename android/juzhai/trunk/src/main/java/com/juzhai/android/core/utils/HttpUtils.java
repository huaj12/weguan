package com.juzhai.android.core.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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
import android.graphics.Bitmap;
import android.net.ConnectivityManager;

import com.juzhai.android.R;
import com.juzhai.android.core.ApplicationContext;
import com.juzhai.android.passport.data.UserCacheManager;
import com.juzhai.android.passport.exception.NeedLoginException;

public class HttpUtils {

	private static int CONNECT_TIMEOUT = 20000;
	private static int READ_TIMEOUT = 30000;

	public static <T> ResponseEntity<T> post(Context context, String uri,
			Map<String, Object> values, Class<T> responseType)
			throws NeedLoginException {
		return post(context, uri, values, null, responseType);
	}

	public static <T> ResponseEntity<T> post(Context context, String uri,
			Map<String, Object> values, Map<String, String> cookies,
			Class<T> responseType) throws NeedLoginException {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (!CollectionUtils.isEmpty(values)) {
			for (Entry<String, Object> entry : values.entrySet()) {
				formData.add(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return post(context, uri, formData, cookies, new MediaType(
				"application", "x-www-form-urlencoded"), responseType);
	}

	public static <T> ResponseEntity<T> uploadFile(Context context, String uri,
			Map<String, Object> values, Map<String, String> cookies,
			String filename, Bitmap file, Class<T> responseType)
			throws NeedLoginException {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (!CollectionUtils.isEmpty(values)) {
			// for (Entry<String, Object> entry : values.entrySet()) {
			// formData.add(entry.getKey(), entry.getValue());
			// }
			uri = createHttpParam(uri, values);
		}
		if (file != null) {
			Resource resource = new ByteArrayResource(ImageUtils.bitmap2Bytes(
					file, 100)) {

				@Override
				public String getFilename() throws IllegalStateException {
					return ImageUtils.getFileName();
				}
			};
			formData.add(filename, resource);
		}
		return post(context, uri, formData, cookies,
				MediaType.MULTIPART_FORM_DATA, responseType);
	}

	private static <T> ResponseEntity<T> post(Context context, String uri,
			MultiValueMap<String, Object> formData,
			Map<String, String> cookies, MediaType mediaType,
			Class<T> responseType) throws NeedLoginException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		prepareCookies(context, cookies, requestHeaders);

		requestHeaders.setContentType(mediaType);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				formData, requestHeaders);
		RestTemplate restTemplate = createRestTemplate(context);
		if (null == restTemplate) {
			throw new RestClientException(
					context.getString(R.string.system_internet_erorr));
		}
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		ApplicationContext config = (ApplicationContext) context
				.getApplicationContext();
		try {
			ResponseEntity<T> responseEntity = restTemplate.exchange(
					config.getBaseUrl() + uri, HttpMethod.POST, requestEntity,
					responseType);
			UserCacheManager.updateTokens(context, responseEntity);
			return responseEntity;
		} catch (RestClientException e) {
			if (e.getCause() instanceof NeedLoginException) {
				throw (NeedLoginException) e.getCause();
			} else {
				throw e;
			}
		}
	}

	private static void prepareCookies(Context context,
			Map<String, String> cookies, HttpHeaders requestHeaders) {
		Map<String, String> userStatusCookies = UserCacheManager.getUserCache(
				context).getUserStatus();
		if (!CollectionUtils.isEmpty(cookies)) {
			userStatusCookies.putAll(cookies);
		}
		for (Entry<String, String> entry : userStatusCookies.entrySet()) {
			requestHeaders.add("Cookie",
					entry.getKey() + "=" + entry.getValue());
		}
	}

	public static <T> ResponseEntity<T> get(Context context, String uri,
			Map<String, Object> values, Class<T> responseType)
			throws NeedLoginException {
		return get(context, uri, values, null, responseType);
	}

	public static <T> ResponseEntity<T> get(Context context, String uri,
			Class<T> responseType) throws NeedLoginException {
		return get(context, uri, null, null, responseType);
	}

	public static <T> ResponseEntity<T> get(Context context, String uri,
			Map<String, Object> values, Map<String, String> cookies,
			Class<T> responseType) throws NeedLoginException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		prepareCookies(context, cookies, requestHeaders);

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
		try {
			ResponseEntity<T> responseEntity = restTemplate.exchange(
					config.getBaseUrl() + createHttpParam(uri, values),
					HttpMethod.GET, requestEntity, responseType);
			UserCacheManager.updateTokens(context, responseEntity);
			return responseEntity;
		} catch (RestClientException e) {
			if (e.getCause() instanceof NeedLoginException) {
				throw (NeedLoginException) e.getCause();
			} else {
				throw e;
			}
		}
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
				if (response != null) {
					if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
						throw new RestClientException("not login",
								new NeedLoginException());
					}
				}
			}
		});
		return restTemplate;
	}
}
