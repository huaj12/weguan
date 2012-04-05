package com.juzhai.promotion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.passport.LockLevelConfig;

public class PromotionConfig {
	private static final Log log = LogFactory.getLog(LockLevelConfig.class);

	private static final String OCCASIONAL_CONFIG_PATH = "properties/occasional.properties";
	private static final String USER_MAN_CONFIG_PATH = "properties/nicemanuser.properties";
	private static final String USER_WOMEN_CONFIG_PATH = "properties/nicewomenuser.properties";
	private static final Random random = new Random();

	private static final List<String> OCCASIONAL_LIST = new ArrayList<String>();
	private static final List<Long> USER_MAN_LIST = new ArrayList<Long>();
	private static final List<Long> USER_WOMEN_LIST = new ArrayList<Long>();

	static {
		initOccasional();
		initManUser();
		initWomenUser();
	}

	private static void initOccasional() {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(OCCASIONAL_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			String key = (String) entry.getKey();
			OCCASIONAL_LIST.add(key);
		}
	}

	private static void initManUser() {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(USER_MAN_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			Long key = Long.parseLong((String) entry.getKey());
			USER_MAN_LIST.add(key);
		}
	}

	private static void initWomenUser() {
		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(USER_WOMEN_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		for (Entry<Object, Object> entry : config.entrySet()) {
			Long key = Long.parseLong((String) entry.getKey());
			USER_WOMEN_LIST.add(key);
		}
	}

	public static String randomOccasional() {
		return OCCASIONAL_LIST.get(random.nextInt(OCCASIONAL_LIST.size()));
	}

	public static Long randomUser(int sex) {
		try {
			if (sex == 1) {
				return USER_MAN_LIST.get(random.nextInt(USER_MAN_LIST.size()));
			} else {
				return USER_WOMEN_LIST.get(random.nextInt(USER_WOMEN_LIST
						.size()));
			}
		} catch (Exception e) {
			return 0l;
		}
	}
}
