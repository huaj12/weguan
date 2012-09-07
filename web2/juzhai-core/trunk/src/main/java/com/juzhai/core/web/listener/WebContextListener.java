package com.juzhai.core.web.listener;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.juzhai.core.util.SpringUtil;

public class WebContextListener extends ContextLoaderListener {
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		SpringUtil
				.setApplicationContext((WebApplicationContext) sce
						.getServletContext()
						.getAttribute(
								WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
		if (log.isDebugEnabled()) {
			log.debug("spring application init");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}
