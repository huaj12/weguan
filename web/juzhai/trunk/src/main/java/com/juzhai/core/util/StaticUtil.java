package com.juzhai.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 视图层工具类
 * 
 * @author wujiajun
 * 
 */
public class StaticUtil {
	private final static Log log = LogFactory.getLog(StaticUtil.class);

	private static String STATIC_CONFIG_PATH = "/properties/static.properties";
	private static String prefixCss = null;
	private static String prefixJs = null;
	private static String prefixImage = null;
	private static String prefixStatic = null;
	private static String version;

	static {
		try {
			init();
		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}

	private static void init() throws IOException {
		InputStream in = StaticUtil.class.getClassLoader().getResourceAsStream(
				STATIC_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /properties/urls.properties can't be found in Classpath.");
		}

		Properties prop = new Properties();
		prop.load(in);

		// configInfo = prop;

		prefixCss = prop.getProperty("prefix_css");
		prefixJs = prop.getProperty("prefix_js");
		prefixImage = prop.getProperty("prefix_image");
		prefixStatic = prop.getProperty("prefix_static");
		version = prop.getProperty("version", "");
	}

	//
	/**
	 * 转换静态文件的url为实际部署的路径.<br>
	 * '/js/scene/a.js' --> 'http://ddd.ccc.c/js/scene.js' <br>
	 * 'js/ss.js' --> 'js/ss.js'
	 * 
	 * @param key
	 * @return
	 */
	public static String u(String key) {
		if (key == null || "".equals(key)) {
			return key;
		}
		key = key.replace("\\", "/");
		int startIndex = key.indexOf("/");
		int endIndex = key.indexOf("/", startIndex + 1);
		String url = StringUtils.EMPTY;
		if (startIndex != -1 && endIndex != -1
				&& startIndex != key.length() - 1 && startIndex < endIndex) {
			String suffix = key.substring(startIndex + 1, endIndex);
			if (suffix.equalsIgnoreCase("css")) {
				url = prefixCss + key;
			} else if (suffix.equalsIgnoreCase("js")) {
				url = prefixJs + key;
			} else if (suffix.equalsIgnoreCase("images")) {
				url = prefixImage + key;
			} else {
				return prefixStatic + key;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("static resource converting:" + key + "->" + url);
		}
		return url + (StringUtils.isEmpty(version) ? "" : ("?" + version));
	}

	public static String getPrefixCss() {
		return prefixCss;
	}

	public static String getPrefixJs() {
		return prefixJs;
	}

	public static String getPrefixImage() {
		return prefixImage;
	}

	public static String getPrefixStatic() {
		return prefixStatic;
	}
}
