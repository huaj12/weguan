/**
 * Created on 2005-6-16
 * 
 * @author bing.xie
 * 
 */
package com.juzhai.wordfilter.web.util;

import org.springframework.context.ApplicationContext;

/**
 * 仅供Web层调用，比如Servlet和JSP
 * 
 * @author gxie
 * 
 */
public class SpringContextUtil {

	private static ApplicationContext context;

	public static void setApplicationContext(ApplicationContext acx) {
		context = acx;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

}