package com.juzhai.spider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.core.SystemConfig;

public class RegexConfig {
	private static final Log log = LogFactory.getLog(SystemConfig.class);

	private static final String FILE_CONFIG_PATH = "properties/regex.properties";

	public static final Map<String, String> REGEXS = new HashMap<String, String>();

	static {
		Properties config = new Properties();
		try {
			config.load(RegexConfig.class.getClassLoader().getResourceAsStream(
					FILE_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			REGEXS.put(key, value);
		}
	}
}
