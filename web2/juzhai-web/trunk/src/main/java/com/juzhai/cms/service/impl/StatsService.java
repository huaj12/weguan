package com.juzhai.cms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.IStatsService;
import com.juzhai.core.stats.counter.registry.CounterServiceRegistry;
import com.juzhai.core.stats.counter.service.ICounter;
import com.juzhai.core.util.DateFormat;

@Service
public class StatsService implements IStatsService, BeanFactoryAware {
	@Autowired
	private BeanFactory beanFactory;
	@Autowired
	private MessageSource messageSource;

	@Override
	public Map<String, Long> getStatsCunter(String beginDate, String endDate) {
		Date begin = null;
		Date end = null;
		try {
			begin = DateUtils.parseDate(beginDate, DateFormat.DATE_PATTERN);
			end = DateUtils.parseDate(endDate, DateFormat.DATE_PATTERN);
		} catch (Exception e) {
			begin = new Date();
			end = new Date();
		}
		Map<String, Long> map = new HashMap<String, Long>(
				CounterServiceRegistry.SERVICE_NAME_SET.size());
		for (String serviceName : CounterServiceRegistry.SERVICE_NAME_SET) {
			ICounter counter = (ICounter) beanFactory.getBean(serviceName
					+ "Counter");
			String key = messageSource.getMessage("stats." + serviceName, null,
					null, Locale.SIMPLIFIED_CHINESE);
			Date beginTime = begin;
			Date endTime = end;
			long value = 0;
			while (beginTime.compareTo(endTime) <= 0) {
				value += counter.get(null, beginTime);
				beginTime = DateUtils.addDays(beginTime, 1);
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
