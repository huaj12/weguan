package com.juzhai.core.stats.counter.service;

import java.util.Date;

public interface ICounter {

	void incr(Object[] primary, long value);

	void decr(Object[] primary, long value);

	long get(Object[] primary, Date date);

	void del(Object[] primary, Date date);
}
