/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.cache.test;

import java.util.concurrent.TimeoutException;

import junit.framework.Assert;
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

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		memcachedClient.shutdown();
	}

	@Test
	public void simpleTest() throws TimeoutException, InterruptedException,
			MemcachedException {
		long start = System.currentTimeMillis();
		String key = "simpleKey";
		String value = "simpleValue";
		Assert.assertTrue(memcachedClient.set(key, 120, value));
		Assert.assertEquals(value, memcachedClient.get(key));
		Assert.assertTrue(memcachedClient.delete(key));
		Assert.assertNull(memcachedClient.get(key));
		System.out.println("use time: " + (System.currentTimeMillis() - start));
	}

	@Test
	public void benchmarkTest() {

	}
}
