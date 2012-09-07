package com.juzhai.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

public class SystemConfig implements FactoryBean<List<String>> {

	private static final Log log = LogFactory.getLog(SystemConfig.class);

	private static final String SYSTEM_CONFIG_PATH = "properties/system.properties";

	private static final String DOMAIN_PROPERTY_SUFFIX = "domain";

	private static final Map<String, String> DOMAIN_MAP = new HashMap<String, String>();
	
	// public static String FEED_REDIRECT_URI;

	// public static String SYSNEW_REDIRECT_URI;

	private List<String> locations = new LinkedList<String>();

	static {
		Properties config = new Properties();
		try {
			config.load(SystemConfig.class.getClassLoader()
					.getResourceAsStream(SYSTEM_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (String propertyName : config.stringPropertyNames()) {
			if (propertyName.endsWith(DOMAIN_PROPERTY_SUFFIX)) {
				DOMAIN_MAP.put(propertyName, config.getProperty(propertyName));
			}
		}

		// FEED_REDIRECT_URI = config.getProperty("feed.redirect.uri");
		// SYSNEW_REDIRECT_URI = config.getProperty("sysnew.redirect.uri");
	}

	/**
	 * 获取主站domain
	 * 
	 * @return
	 */
	public static String getDomain() {
		return DOMAIN_MAP.get(DOMAIN_PROPERTY_SUFFIX);
	}

	/**
	 * 获取各平台的domain
	 * 
	 * @param thirdpartyName
	 * @return
	 */
	public static String getDomain(String thirdpartyName) {
		if (StringUtils.isEmpty(thirdpartyName)) {
			return getDomain();
		} else {
			return DOMAIN_MAP
					.get(thirdpartyName + "." + DOMAIN_PROPERTY_SUFFIX);
		}
	}

	/**
	 * 根据当前请求URL获取哪个平台
	 * 
	 * @param thirdpartyName
	 * @return
	 */
	public static String getThirdpartyName(String requestUrl) {
		String key = null;
		for (Map.Entry<String, String> entry : SystemConfig.DOMAIN_MAP
				.entrySet()) {
			if (StringUtils.contains(requestUrl, entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		if (StringUtils.isEmpty(key)
				|| StringUtils.equals(DOMAIN_PROPERTY_SUFFIX, key)) {
			return null;
		} else {
			return StringUtils.substring(key, 0,
					StringUtils.lastIndexOf(key, "." + DOMAIN_PROPERTY_SUFFIX));
		}
	}

	public SystemConfig() {
		super();
		locations.add("classpath:" + SYSTEM_CONFIG_PATH);
	}

	public void setLocations(List<String> locations) {
		this.locations.addAll(locations);
	}

	@Override
	public List<String> getObject() throws Exception {
		return locations;
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
