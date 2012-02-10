package com.juzhai.notice;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.juzhai.passport.bean.ThirdpartyNameEnum;

public class NoticeConfig {
	private static final String FILE_CONFIG_PATH = "properties/notice.properties";
	private static final Map<String, Map<String, Long>> SECRETARY = new HashMap<String, Map<String, Long>>();
	static {
		InputStream in = NoticeConfig.class.getClassLoader()
				.getResourceAsStream(FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /porperties/notice.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				for (ThirdpartyNameEnum t : ThirdpartyNameEnum.values()) {
					if (key.indexOf(t.getName()) != -1) {
						Map<String, Long> map = SECRETARY.get(t.getName());
						if (map == null) {
							map = new HashMap<String, Long>();
							SECRETARY.put(t.getName(), map);
						}
						map.put(key, Long.valueOf(value));
					}
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}

	public static Long getValue(ThirdpartyNameEnum tager, String name) {
		Map<String, Long> map = SECRETARY.get(tager.getName());
		return map.get(tager.getName() + "." + name);
	}
}
