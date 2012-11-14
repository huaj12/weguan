package com.juzhai.notice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IosPushInfo {

	private static final String FILE_CONFIG_PATH = "/properties/iospush.properties";
	private static String password;
	private static String sound;
	private static boolean production;
	private static String keystore;

	static {
		InputStream in = IosPushInfo.class.getClassLoader()
				.getResourceAsStream(FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /properties/file.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			password = prop.getProperty("ios.push.password");
			sound = prop.getProperty("ios.push.sound");
			production = Boolean.valueOf(prop
					.getProperty("ios.push.production"));
			keystore = prop.getProperty("ios.push.keystore");
		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}

	public static String getPassword() {
		return password;
	}

	public static String getSound() {
		return sound;
	}

	public static boolean isProduction() {
		return production;
	}

	public static InputStream getKeystore() {
		return IosPushInfo.class.getClassLoader().getResourceAsStream(keystore);
	}
}
