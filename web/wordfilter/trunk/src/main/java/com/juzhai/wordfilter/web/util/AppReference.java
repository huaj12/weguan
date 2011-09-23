/* 
 * SpamDataUtil.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author xiaolin
 * 
 *         2008-3-17 create
 */
public class AppReference {
	private static final Logger logger = Logger.getLogger(AppReference.class);
	public static final int UNDEFINEDID = -1;

	private Map<Integer, String> id_AppMap = null;
	private Properties app_IdProperties;

	private String fileName = "app_id.properties";

	public AppReference() {
		id_AppMap = new HashMap<Integer, String>();
	}

	public void loadAppInfo(String file) {
		// if(file==null||!file.exists())
		// throw new
		// IllegalArgumentException("System can't load specified file");
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(file);
		if (inputStream == null)
			throw new IllegalArgumentException(
					"System can't load specified file:" + file);

		app_IdProperties = new Properties();
		try {
			app_IdProperties.load(inputStream);
		} catch (Exception e) {
			logger.error("loading app information failed!" + e.getMessage(), e);
			return;
		}
		if (app_IdProperties != null) {
			Iterator<Object> keys = app_IdProperties.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next().toString();

				String value = app_IdProperties.getProperty(key);
				int id = -1;
				try {
					id = Integer.parseInt(value);
				} catch (Exception e) {
					throw new RuntimeException(
							"id value in property file must be a number");
				}
				id_AppMap.put(id, key);
			}
		}
	}

	public int getIdByAppName(String appName) {
		int value;
		try {
			value = Integer.parseInt(app_IdProperties.getProperty(appName));
		} catch (Exception e) {
			value = -1;
		}
		return value;
	}

	public String getAppNameById(int id) {
		return id_AppMap.get(id);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		loadAppInfo(fileName);
	}
}
