package com.juzhai.core.stats.counter.service;

import java.util.Date;

import com.juzhai.core.stats.counter.server.ICounterServer;

public class Counter extends AbstractCounter {

	private String serviceName;
	private StatsTimeUnit timeUnit;
	/**
	 * unsupport now
	 */
	private boolean asyn;
	private ICounterServer counterServer;

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setTimeUnit(StatsTimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public void setAsyn(boolean asyn) {
		this.asyn = asyn;
	}

	public void setCounterServer(ICounterServer counterServer) {
		this.counterServer = counterServer;
	}

	@Override
	public void incr(Object[] primary, long value) {
		this.counterServer.incr(
				buildKey(serviceName, primary, new Date(), timeUnit), value);
	}

	@Override
	public void decr(Object[] primary, long value) {
		this.counterServer.decr(
				buildKey(serviceName, primary, new Date(), timeUnit), value);
	}

	@Override
	public long get(Object[] primary, Date date) {
		if (date == null) {
			date = new Date();
		}
		return this.counterServer.get(buildKey(serviceName, primary, date,
				timeUnit));
	}

	@Override
	public void del(Object[] primary, Date date) {
		if (date == null) {
			date = new Date();
		}
		this.counterServer.get(buildKey(serviceName, primary, date, timeUnit));
	}
}
