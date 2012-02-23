package com.juzhai.stats.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.core.util.DateFormat;
import com.juzhai.stats.counter.CounterServiceRegistry;
import com.juzhai.stats.counter.service.ICounter;
import com.juzhai.stats.service.IStatsService;

@Service
public class StatsService implements IStatsService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private MessageSource messageSource;

	@Override
	public Map<String, Long> getStatsCunter(String beginDate, String endDate) {
		Date begin;
		Date end;
		try {
			begin = DateUtils.parseDate(beginDate, DateFormat.DATE_PATTERN);
			end = DateUtils.parseDate(endDate, DateFormat.DATE_PATTERN);
		} catch (Exception e) {
			begin = new Date();
			end = new Date();
		}
		String serviceNames = CounterServiceRegistry.p
				.getProperty(CounterServiceRegistry.COUNTER_SERVICES_KEY);
		StringTokenizer st = new StringTokenizer(serviceNames, "|");
		HashMap<String, Long> map = new HashMap<String, Long>();
		while (st.hasMoreTokens()) {
			String serviceName = st.nextToken();
			ICounter counter = (ICounter) beanFactory.getBean(serviceName
					+ "Counter");
			String key = messageSource.getMessage(serviceName, null, null,
					Locale.SIMPLIFIED_CHINESE);
			Calendar ca = Calendar.getInstance();
			Date beginTime = begin;
			Date endTime = end;
			long value = 0;
			while (beginTime.compareTo(endTime) <= 0) {
				value += counter.get(null, beginTime);
				ca.setTime(beginTime);
				ca.add(ca.DATE, 1);
				beginTime = ca.getTime();
			}
			map.put(key, value);
		}
		return map;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

}
