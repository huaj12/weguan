/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.cache.test;

import java.util.concurrent.TimeoutException;

import junit.framework.Assert;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class MemcachedTest {

	@Autowired
	private MemcachedClient memcachedClient;
	private String key = "simpleKey1";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		memcachedClient.delete(key);
		memcachedClient.shutdown();
	}

	@Test
	public void simpleTest() throws TimeoutException, InterruptedException,
			MemcachedException {
		long start = System.currentTimeMillis();
		String value = "simpleValue";
		Assert.assertTrue(memcachedClient.set(key, 120, value));
		Assert.assertEquals(value, memcachedClient.get(key));
		// Assert.assertTrue(memcachedClient.touch(key, 120));
		Assert.assertTrue(memcachedClient.delete(key));
		Assert.assertNull(memcachedClient.get(key));
		System.out.println("use time: " + (System.currentTimeMillis() - start));
	}

	@Test
	public void incr() throws TimeoutException, InterruptedException,
			MemcachedException {
		// Counter counter = memcachedClient.getCounter(key);
		// System.out.println(counter.get());
		System.out.println(memcachedClient.get(key));
		Assert.assertEquals(1L, memcachedClient.incr(key, 1L, 1L, 1000L, 2));
		System.out.println(memcachedClient.getCounter(key).get());
		System.out.println(memcachedClient.get(key));
		Assert.assertEquals(2L, memcachedClient.incr(key, 1L, 1L, 1000L, 4));
		System.out.println(memcachedClient.getCounter(key).get());
		System.out.println(memcachedClient.get(key));
		Thread.sleep(3000L);
		System.out.println(memcachedClient.getCounter(key).get());
		System.out.println(memcachedClient.get(key));
	}

	@Test
	public void benchmarkTest() {

	}
}
