/* 
 * PropertiesInfo.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.core.ConfigException;

/**
 * It is a singleton class. This class is used to manage Application config
 * information, including reading from local file and writing into local file.
 * 
 * @author xiaolin
 * 
 *         2008-5-6 create
 */
public class PropertiesInfo {
	private static PropertiesInfo instance;

	private Map<String, Map<String, String>> result;

	private PropertiesInfo() {
		result = new HashMap<String, Map<String, String>>();
		Properties props = new Properties();
		InputStream in = PropertiesInfo.class.getClassLoader()
				.getResourceAsStream("config.properties");
		try {
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// get all the keys from resourceBundle
		Enumeration<Object> keys = props.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String[] groupKey = getGroupKey(key);

			// group properties from resourceBundle
			if (result.containsKey(groupKey[0])) {
				result.get(groupKey[0])
						.put(groupKey[1], props.getProperty(key));
			} else {
				Map<String, String> tempValue = new HashMap<String, String>();
				tempValue.put(groupKey[1], props.getProperty(key));
				result.put(groupKey[0], tempValue);
			}
		}
	}

	public static PropertiesInfo getInstance() {
		if (instance == null)
			instance = new PropertiesInfo();
		return instance;
	}

	/**
	 */
	public Map<String, String> get(String region) {
		return result.get(region);
	}

	public List<String> getKeys(String region) {
		return new ArrayList<String>(result.get(region).keySet());
	}

	public List<String> getValues(String region) {
		return new ArrayList<String>(result.get(region).values());
	}

	public String getValue(String region, String key) {
		return result.get(region).get(key);
	}

	public int getIntParamValue(String key) {
		String v = get(PropertyConstant.REGION_PARAM).get(key);
		try {
			return Integer.parseInt(v);
		} catch (Exception e) {
			System.out.println("Can't transform value:" + v
					+ " to a integer value , key:"
					+ PropertyConstant.REGION_PARAM + "." + key);
			return 0;
		}
	}

	public long getLongParamValue(String key) {
		String v = get(PropertyConstant.REGION_PARAM).get(key);
		try {
			return Long.parseLong(v);
		} catch (Exception e) {
			System.out.println("Can't transform value:" + v
					+ " to a long value , key:" + PropertyConstant.REGION_PARAM
					+ "." + key);
			return 0;
		}
	}

	public float getFloatParamValue(String key) {
		String v = get(PropertyConstant.REGION_PARAM).get(key);
		try {
			return Float.parseFloat(v);
		} catch (Exception e) {
			System.out.println("Can't transform value:" + v
					+ " to a float value , key:"
					+ PropertyConstant.REGION_PARAM + "." + key);
			return 0;
		}
	}

	public String getParamValue(String key) {
		return get(PropertyConstant.REGION_PARAM).get(key);
	}

	/**
	 * Read application config information. This method will parse config text
	 * as a integer array. If an error occurs in parsing a application config,
	 * the config of this application will be ingored.
	 */
	public int[] getAppConfig() {
		Map<String, String> appConfig = get(PropertyConstant.REGION_APPCONFIG);
		if (appConfig == null)
			return new int[0];
		int[] result = new int[appConfig.size()];
		Iterator<String> it = appConfig.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			int index = -1;
			try {
				index = Integer.parseInt(key);
			} catch (Exception e) {
				System.out
						.println("------warning------------:appconfig error, key is not a integer value,key:"
								+ key);
				continue;
			}
			if (index > result.length - 1) {
				System.out
						.println("------warning------------:appconfig error, index according to key is out of bound ,key:"
								+ key);
				continue;
			}
			try {
				result[index] = parseAppConfig(appConfig.get(key));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		return result;
	}

	public Map<Integer, Integer> getAppDeadScoreConfig() {
		Map<String, String> appConfig = get(PropertyConstant.REGION_SPAMTEXTDEADSCORE);
		Map<Integer, Integer> deadScoreMap = new HashMap<Integer, Integer>(
				appConfig.size());
		Iterator<String> keyIt = appConfig.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			String value = appConfig.get(key);
			try {
				Integer intKey = Integer.parseInt(key);
				Integer intValue = Integer.parseInt(value);

				deadScoreMap.put(intKey, intValue);
			} catch (Exception e) {
				System.out
						.println("------warning------------:app dead score config error,the key or value is not a integer value ,key:"
								+ key + ",value:" + value);
				continue;
			}
		}
		return deadScoreMap;
	}

	private int parseAppConfig(String str) throws ConfigException {
		if (str == null || str.trim().equals(""))
			return Config.DoAll;

		String[] steps = str.split("\\|");
		int v = 0;
		for (int i = 0; i < steps.length; i++) {
			Integer tmp = Config.stepMap.get(steps[i]);
			if (tmp == null)
				throw new ConfigException("Can't find step:" + steps[i]
						+ ", according to config:" + str);
			v = v | Config.stepMap.get(steps[i]);
		}
		return v;
	}

	private String[] getGroupKey(String key) {
		String[] result = new String[2];
		result[0] = key.substring(0, key.indexOf("."));
		result[1] = key.substring(key.indexOf(".") + 1);
		return result;
	}
}