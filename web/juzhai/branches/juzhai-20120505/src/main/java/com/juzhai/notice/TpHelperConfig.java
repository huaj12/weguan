package com.juzhai.notice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;

import com.juzhai.passport.bean.ThirdpartyNameEnum;

public class TpHelperConfig {
	private static final String FILE_CONFIG_PATH = "properties/tpHelper.properties";
	private static final Map<String, Map<String, List<Long>>> SECRETARY = new HashMap<String, Map<String, List<Long>>>();
	static {
		InputStream in = TpHelperConfig.class.getClassLoader()
				.getResourceAsStream(FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /porperties/tpHelper.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				for (ThirdpartyNameEnum t : ThirdpartyNameEnum.values()) {
					if (key.indexOf(t.getName()) != -1) {
						Map<String, List<Long>> map = SECRETARY
								.get(t.getName());
						if (map == null) {
							map = new HashMap<String, List<Long>>();
							SECRETARY.put(t.getName(), map);
						}
						List<Long> values = map.get(getName(key));
						if (CollectionUtils.isEmpty(values)) {
							values = new ArrayList<Long>();
						}
						values.add(Long.valueOf(value));
						map.put(getName(key), values);
					}
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}

	public static List<Long> getValue(ThirdpartyNameEnum tager, String name) {
		Map<String, List<Long>> map = SECRETARY.get(tager.getName());
		if (map == null) {
			return null;
		}
		return map.get(name);
	}

	private static String getName(String str) {
		return str.substring(str.indexOf(".") + 1, str.lastIndexOf("."));
	}
}
