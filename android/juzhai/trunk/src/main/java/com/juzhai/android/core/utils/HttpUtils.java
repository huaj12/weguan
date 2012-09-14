package com.juzhai.android.core.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.juzhai.android.core.SystemConfig;

public class HttpUtils {
	public static <T> ResponseEntity<T> post(String uri,
			Map<String, String> values, Class<T> responseType) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		requestHeaders.setContentType(new MediaType("application",
				"x-www-form-urlencoded"));
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		for (Entry<String, String> entry : values.entrySet()) {
			formData.add(entry.getKey(), entry.getValue());
		}
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				formData, requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		ResponseEntity<T> responseEntity = restTemplate.exchange(
				SystemConfig.BASEURL + uri, HttpMethod.POST, requestEntity,
				responseType);
		return responseEntity;
	}
}
