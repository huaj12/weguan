package com.juzhai.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

public class SystemConfig implements FactoryBean<List<String>> {

	private static final Log log = LogFactory.getLog(SystemConfig.class);

	private static final String SYSTEM_CONFIG_PATH = "system.properties";

	public static String BASIC_DOMAIN;

	public static String STATIC_DOMAIN;

	private List<String> locations = new LinkedList<String>();

	static {
		Properties config = new Properties();
		try {
			config.load(SystemConfig.class.getClassLoader()
					.getResourceAsStream(SYSTEM_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		STATIC_DOMAIN = config.getProperty("httpService.static");
		BASIC_DOMAIN = config.getProperty("httpService.basic");
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
