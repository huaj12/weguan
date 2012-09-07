package com.juzhai.core.stats.counter.service;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.juzhai.core.cache.KeyGenerator;

public abstract class AbstractCounter implements ICounter {

	protected String buildKey(String serviceName, Object[] primary, Date date,
			StatsTimeUnit timeUnit) {
		if (date == null) {
			date = new Date();
		}
		StringBuilder key = new StringBuilder();
		key.append(serviceName);
		if (null != primary) {
			for (Object obj : primary) {
				key.append(KeyGenerator.CACHE_KEY_SEPARATOR).append(obj);
			}
		}
		if (null != timeUnit && null != timeUnit.getField()) {
			key.append(KeyGenerator.CACHE_KEY_SEPARATOR).append(
					DateUtils.truncate(date, timeUnit.getField()).getTime());
		}
		return key.toString();
	}
}
