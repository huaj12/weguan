package com.juzhai.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

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

	public static Map<String, String> STATIC_VERSION_MAP = new ConcurrentHashMap<String, String>();

	public static String JS_FILE_ROOT_PATH = null;
	public static String CSS_FILE_ROOT_PATH = null;
	private static String STATIC_CONFIG_PATH = "/properties/static.properties";
	private static String prefixCss = null;
	private static String prefixJs = null;
	private static String prefixImage = null;
	private static String prefixStatic = null;

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
		JS_FILE_ROOT_PATH = prop.getProperty("js_file_root_path");
		CSS_FILE_ROOT_PATH = prop.getProperty("css_file_root_path");
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
		String version = STATIC_VERSION_MAP.get(key);
		return url + (StringUtils.isEmpty(version) ? "" : ("?v=" + version));
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
