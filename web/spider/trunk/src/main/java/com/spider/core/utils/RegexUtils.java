package com.spider.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.spider.core.bean.Target;

public class RegexUtils {
	private static final String FILE_CONFIG_PATH = "porperties/regex.properties";
	public static final Map<String, Map<String, String>> TARGET_REGEX = new HashMap<String, Map<String, String>>();
	static {
		InputStream in = RegexUtils.class.getClassLoader().getResourceAsStream(
				FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /properties/regex.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				for (Target t : Target.values()) {
					if (key.indexOf(t.getName()) != -1) {
						Map<String, String> map = TARGET_REGEX.get(t.getName());
						if (map == null) {
							map = new HashMap<String, String>();
							TARGET_REGEX.put(t.getName(), map);
						}
						map.put(key, value);
					}
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}
}
