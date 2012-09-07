package com.juzhai.core.stats.counter.registry;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.juzhai.core.stats.counter.server.ICounterServer;
import com.juzhai.core.stats.counter.service.StatsTimeUnit;

public class CounterServiceRegistry implements BeanFactoryPostProcessor,
		InitializingBean {

	private final Log log = LogFactory.getLog(getClass());

	private static final String STATS_CONFIG_PATH = "properties/stats.properties";
	private static final String DEFAULT_ASYN = "false";
	private static final String DEFAULT_SERVER = "redis";
	private static final String DEFAULT_TIMEUNIT = "DATE";

	private static final String COUNTER_SERVICES_KEY = "counter.services";
	private static final String ASYN_KEY_SUFFIX = ".asyn";
	private static final String TIMEUNIT_KEY_SUFFIX = ".timeunit";
	private static final String SERVER_KEY_SUFFIX = ".server";

	private static final String COUNTER_BEAN_NAME_SUFFIX = "Counter";
	private static final String SERVER_BEAN_NAME_SUFFIX = "CounterServer";

	private static Properties p = new Properties();

	public static Set<String> SERVICE_NAME_SET = new HashSet<String>();

	private void registerCounter(ConfigurableListableBeanFactory beanFactory,
			String serviceName) {
		boolean isAsyn = Boolean.valueOf(p.getProperty(serviceName
				+ ASYN_KEY_SUFFIX, DEFAULT_ASYN));
		StatsTimeUnit timeUnit = StatsTimeUnit.valueOf(p.getProperty(
				serviceName + TIMEUNIT_KEY_SUFFIX, DEFAULT_TIMEUNIT));
		String server = p.getProperty(serviceName + SERVER_KEY_SUFFIX,
				DEFAULT_SERVER);
		ICounterServer counterServer = (ICounterServer) beanFactory
				.getBean(server + SERVER_BEAN_NAME_SUFFIX);
		if (null != timeUnit && null != counterServer) {
			beanFactory.registerSingleton(serviceName
					+ COUNTER_BEAN_NAME_SUFFIX, CounterFactory.newCounter(
					serviceName, timeUnit, counterServer, isAsyn));
		}
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (p.isEmpty()) {
			return;
		}
		String serviceNames = p.getProperty(COUNTER_SERVICES_KEY);
		StringTokenizer st = new StringTokenizer(serviceNames, "|");
		while (st.hasMoreTokens()) {
			String serviceName = st.nextToken();
			if (StringUtils.isNotEmpty(serviceName)
					&& !SERVICE_NAME_SET.contains(serviceName)) {
				registerCounter(beanFactory, serviceName);
				SERVICE_NAME_SET.add(serviceName);
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			p.load(getClass().getClassLoader().getResourceAsStream(
					STATS_CONFIG_PATH));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
