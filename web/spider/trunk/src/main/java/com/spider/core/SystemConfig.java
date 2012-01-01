package com.spider.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.spider.core.bean.Target;
import com.spider.core.utils.RegexUtils;

public class SystemConfig {
	private static final String FILE_CONFIG_PATH = "porperties/system.properties";
	public static final Map<String, String> DOMAIN = new HashMap<String, String>();
	static {
		InputStream in = RegexUtils.class.getClassLoader().getResourceAsStream(
				FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /properties/system.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				for (Target t : Target.values()) {
					if (key.indexOf(t.getName()) != -1) {
						DOMAIN.put(t.getName(), value);
					}
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}
}
