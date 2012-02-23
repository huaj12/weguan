package com.juzhai.stats.counter.registry;

import com.juzhai.stats.counter.server.ICounterServer;
import com.juzhai.stats.counter.service.Counter;
import com.juzhai.stats.counter.service.ICounter;
import com.juzhai.stats.counter.service.StatsTimeUnit;

public class CounterFactory {

	public static ICounter newCounter(String serviceName,
			StatsTimeUnit timeUnit, ICounterServer counterServer, boolean isAsyn) {
		Counter counter = new Counter();
		counter.setServiceName(serviceName);
		counter.setTimeUnit(timeUnit);
		counter.setCounterServer(counterServer);
		counter.setAsyn(isAsyn);
		return counter;
	}
}
