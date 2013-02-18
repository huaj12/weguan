package com.juzhai.dpsdk.model;

import java.security.AccessControlException;
import java.util.Properties;

public class Configuration {
	private static Properties defaultProperty;

	static {
		init();
	}

	/* package */static void init() {
		defaultProperty = new Properties();
		defaultProperty.setProperty("dianping.debug", "true");
		defaultProperty.setProperty("dianping.http.useSSL", "false");
		defaultProperty.setProperty("dianping.http.proxyHost.fallback",
				"http.proxyHost");
		defaultProperty.setProperty("dianping.http.proxyPort.fallback",
				"http.proxyPort");
		defaultProperty.setProperty("dianping.http.connectionTimeout", "20000");
		defaultProperty.setProperty("dianping.http.readTimeout", "120000");
		defaultProperty.setProperty("dianping.clientVersion",
				Version.getVersion());
	}

	public static boolean useSSL() {
		return getBoolean("dianping.http.useSSL");
	}

	public static String getScheme() {
		return useSSL() ? "https://" : "http://";
	}

	public static String getProxyHost() {
		return getProperty("dianping.http.proxyHost");
	}

	public static String getProxyHost(String proxyHost) {
		return getProperty("dianping.http.proxyHost", proxyHost);
	}

	public static int getProxyPort() {
		return getIntProperty("dianping.http.proxyPort");
	}

	public static int getProxyPort(int port) {
		return getIntProperty("dianping.http.proxyPort", port);
	}

	public static String getProxyUser() {
		return getProperty("dianping.http.proxyUser");
	}

	public static String getProxyUser(String user) {
		return getProperty("dianping.http.proxyUser", user);
	}

	public static String getProxyPassword() {
		return getProperty("dianping.http.proxyPassword");
	}

	public static String getProxyPassword(String password) {
		return getProperty("dianping.http.proxyPassword", password);
	}

	public static int getConnectionTimeout() {
		return getIntProperty("dianping.http.connectionTimeout");
	}

	public static int getConnectionTimeout(int connectionTimeout) {
		return getIntProperty("dianping.http.connectionTimeout",
				connectionTimeout);
	}

	public static int getReadTimeout() {
		return getIntProperty("dianping.http.readTimeout");
	}

	public static int getReadTimeout(int readTimeout) {
		return getIntProperty("dianping.http.readTimeout", readTimeout);
	}

	public static String getCilentVersion() {
		return getProperty("dianping.clientVersion");
	}

	public static boolean getBoolean(String name) {
		String value = getProperty(name);
		return Boolean.valueOf(value);
	}

	public static int getIntProperty(String name) {
		String value = getProperty(name);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static int getIntProperty(String name, int fallbackValue) {
		String value = getProperty(name, String.valueOf(fallbackValue));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static long getLongProperty(String name) {
		String value = getProperty(name);
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static String getProperty(String name) {
		return getProperty(name, null);
	}

	public static String getProperty(String name, String fallbackValue) {
		String value;
		try {
			value = System.getProperty(name, fallbackValue);
			if (null == value) {
				value = defaultProperty.getProperty(name);
			}
			if (null == value) {
				String fallback = defaultProperty.getProperty(name
						+ ".fallback");
				if (null != fallback) {
					value = System.getProperty(fallback);
				}
			}
		} catch (AccessControlException ace) {
			// Unsigned applet cannot access System properties
			value = fallbackValue;
		}
		return replace(value);
	}

	private static String replace(String value) {
		if (null == value) {
			return value;
		}
		String newValue = value;
		int openBrace = 0;
		if (-1 != (openBrace = value.indexOf("{", openBrace))) {
			int closeBrace = value.indexOf("}", openBrace);
			if (closeBrace > (openBrace + 1)) {
				String name = value.substring(openBrace + 1, closeBrace);
				if (name.length() > 0) {
					newValue = value.substring(0, openBrace)
							+ getProperty(name)
							+ value.substring(closeBrace + 1);

				}
			}
		}
		if (newValue.equals(value)) {
			return value;
		} else {
			return replace(newValue);
		}
	}

	public static boolean getDebug() {
		return getBoolean("dianping.debug");

	}
}
