package com.juzhai.android.core.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.graphics.Bitmap;

import com.juzhai.android.core.SystemConfig;

public class HttpUtils {
	public static <T> ResponseEntity<T> post(String uri,
			Map<String, String> values, Class<T> responseType) {

		return post(uri, values, null, responseType);
	}

	private static <T> ResponseEntity<T> post(String uri,
			MultiValueMap<String, Object> formData,
			Map<String, String> cookies, MediaType mediaType,
			Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		if (!CollectionUtils.isEmpty(cookies)) {
			for (Entry<String, String> entry : cookies.entrySet()) {
				requestHeaders.add("Cookie",
						entry.getKey() + "=" + entry.getValue());
			}
		}
		requestHeaders.setContentType(mediaType);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				formData, requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new ResourceHttpMessageConverter());
		ResponseEntity<T> responseEntity = restTemplate.exchange(
				SystemConfig.BASEURL + uri, HttpMethod.POST, requestEntity,
				responseType);
		return responseEntity;
	}

	public static <T> ResponseEntity<T> get(String uri, Class<T> responseType) {
		return get(uri, null, responseType);
	}

	public static <T> ResponseEntity<T> get(String uri,
			Map<String, String> cookies, Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		if (!CollectionUtils.isEmpty(cookies)) {
			for (Entry<String, String> entry : cookies.entrySet()) {
				requestHeaders.add("Cookie",
						entry.getKey() + "=" + entry.getValue());
			}
		}
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(
				requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		ResponseEntity<T> responseEntity = restTemplate.exchange(
				SystemConfig.BASEURL + uri, HttpMethod.GET, requestEntity,
				responseType);
		return responseEntity;
	}

	public static String createHttpParam(String uri, Map<String, Object> values) {
		// TODO (done) 一定要“?”结尾？
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

	public static <T> ResponseEntity<T> uploadFile(String uri,
			Map<String, String> values, Map<String, String> cookies,
			String filename, Bitmap file, Class<T> responseType) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (!CollectionUtils.isEmpty(values)) {
			for (Entry<String, String> entry : values.entrySet()) {
				formData.add(entry.getKey(), entry.getValue());
			}
		}
		if (file != null) {
			Resource resource = new ByteArrayResource(
					ImageUtils.Bitmap2Bytes(file)) {

				@Override
				public String getFilename() throws IllegalStateException {
					return ImageUtils.getFileName();
				}

			};
			formData.add(filename, resource);
		}
		return post(uri, formData, cookies, new MediaType("multipart",
				"form-data"), responseType);
	}

	public static <T> ResponseEntity<T> post(String uri,
			Map<String, String> values, Map<String, String> cookies,
			Class<T> responseType) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (!CollectionUtils.isEmpty(values)) {
			for (Entry<String, String> entry : values.entrySet()) {
				formData.add(entry.getKey(), entry.getValue());
			}
		}
		return post(uri, formData, cookies, new MediaType("application",
				"x-www-form-urlencoded"), responseType);
	}
}
