package com.juzhai.core.stats.counter.server;

public interface ICounterServer {

	void incr(String key, long value);

	void decr(String key, long value);

	long get(String key);

	void del(String key);
}
