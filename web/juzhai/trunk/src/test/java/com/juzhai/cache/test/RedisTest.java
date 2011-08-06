/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.cache.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class RedisTest {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	private String key = "simpleKey";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {

		redisTemplate.delete(key);
	}

	@Test
	public void simpleTest() {
		long value1 = 12345L;
		double score1 = 50D;
		long value2 = 1234L;
		double score2 = 25D;

		Assert.assertTrue(redisTemplate.opsForZSet().add(key, value1, score1));
		Assert.assertTrue(redisTemplate.opsForZSet().add(key, value2, score2));

		// size
		Assert.assertEquals(2L, redisTemplate.opsForZSet().size(key)
				.longValue());

		// score
		Assert.assertEquals(score1,
				redisTemplate.opsForZSet().score(key, value1));
		Assert.assertEquals(score2,
				redisTemplate.opsForZSet().score(key, value2));

		// rank
		Assert.assertEquals(0, redisTemplate.opsForZSet().rank(key, value2)
				.longValue());
		Assert.assertEquals(1, redisTemplate.opsForZSet().rank(key, value1)
				.longValue());

		// reverseRank
		Assert.assertEquals(1,
				redisTemplate.opsForZSet().reverseRank(key, value2).longValue());
		Assert.assertEquals(0,
				redisTemplate.opsForZSet().reverseRank(key, value1).longValue());

		// range
		Assert.assertEquals(value2, redisTemplate.opsForZSet().range(key, 0, 0)
				.iterator().next().longValue());

		// reverseRange
		Assert.assertEquals(value1,
				redisTemplate.opsForZSet().reverseRange(key, 0, 0).iterator()
						.next().longValue());

		// incrementScore
		Assert.assertEquals(score1 - 30D, redisTemplate.opsForZSet()
				.incrementScore(key, value1, -30D).doubleValue());
		Assert.assertEquals(score2 + 30D, redisTemplate.opsForZSet()
				.incrementScore(key, value2, 30D).doubleValue());
		Assert.assertEquals(value1, redisTemplate.opsForZSet().range(key, 0, 0)
				.iterator().next().longValue());
		Assert.assertEquals(value2,
				redisTemplate.opsForZSet().reverseRange(key, 0, 0).iterator()
						.next().longValue());

		redisTemplate.opsForZSet().removeRange(key, 0, 0);
		// size
		Assert.assertEquals(1L, redisTemplate.opsForZSet().size(key)
				.longValue());
		// range
		Assert.assertEquals(value2, redisTemplate.opsForZSet().range(key, 0, 0)
				.iterator().next().longValue());
	}
}
