/**
 * Created on 2008-03-03
 * 
 * @author bing.xie
 * 
 */

package com.juzhai.wordfilter.web.listener;

/**
 * 
 * @author xiaolin
 *
 */
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.juzhai.wordfilter.web.util.SpringContextUtil;

public class SpringContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {

		super.contextInitialized(event);
		SpringContextUtil.setApplicationContext(WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext()));
	}
}