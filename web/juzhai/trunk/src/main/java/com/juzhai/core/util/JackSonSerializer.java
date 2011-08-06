package com.juzhai.core.util;

import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JackSonSerializer {

	private static ObjectMapper m = new ObjectMapper();

	public static <T> T toBean(String jsonAsString, Class<T> pojoClass)
			throws JsonGenerationException {
		try {
			return m.readValue(jsonAsString, pojoClass);
		} catch (Exception e) {
			throw new JsonGenerationException(e);
		}
	}

	public static String toString(Object pojo) throws JsonGenerationException {
		try {
			StringWriter sw = new StringWriter();
			m.writeValue(sw, pojo);
			return sw.toString();
		} catch (Exception e) {
			throw new JsonGenerationException(e);
		}
	}

	public static <T> Map<String, T> toMap(String jsonAsString)
			throws JsonGenerationException {
		try {
			return m.readValue(jsonAsString,
					new TypeReference<Map<String, T>>() {
					});
		} catch (Exception e) {
			throw new JsonGenerationException(e);
		}
	}

	public static <T> Map<String, T>[] toMapArray(String jsonAsString)
			throws JsonGenerationException {
		try {
			return m.readValue(jsonAsString,
					new TypeReference<Map<String, T>[]>() {
					});
		} catch (Exception e) {
			throw new JsonGenerationException(e);
		}
	}
}
