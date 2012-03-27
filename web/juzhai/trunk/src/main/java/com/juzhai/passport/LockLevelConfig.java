package com.juzhai.passport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LockLevelConfig {

	private static final Log log = LogFactory.getLog(LockLevelConfig.class);

	private static final String SYSTEM_CONFIG_PATH = "properties/lockLevel.properties";

	public static final Map<String, Long> LOCKLEVEL_MAP = new HashMap<String, Long>();

	static {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(SYSTEM_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String key = (String) entry.getKey();
			Long value = Long.parseLong((String) entry.getValue());
			LOCKLEVEL_MAP.put(key, value);
		}
	}

}
