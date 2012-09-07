/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.cache.test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.mail.bean.Mail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class RedisTest {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, Integer> integerRedisTemplate;
	@Autowired
	private RedisTemplate<String, Mail> mailRedisTemplate;
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

	@Test
	public void listRemoveTest() {
		long value1 = 1L;
		long value2 = 2L;
		long value3 = 3L;
		long value4 = 4L;
		redisTemplate.opsForList().leftPush(key, value1);
		redisTemplate.opsForList().leftPush(key, value2);
		redisTemplate.opsForList().leftPush(key, value3);
		redisTemplate.opsForList().leftPush(key, value4);
		redisTemplate.opsForList().leftPush(key, value1);
		redisTemplate.opsForList().leftPush(key, value2);

		redisTemplate.opsForList().remove(key, -1, value1);
		List<Long> returnValues = redisTemplate.opsForList().range(key, 0, -1);
		for (long value : returnValues) {
			System.out.println(value);
		}
	}

	// @Test
	// public void emailQueue() {
	// while (true) {
	// Mail mail = mailRedisTemplate.opsForList().leftPop(
	// RedisKeyGenerator.genMailQueueKey(), 5, TimeUnit.SECONDS);
	// System.out.println(mail.getReceiver().getEmailAddress());
	// }
	// }

	@Test
	public void testIncr() {
		System.out
				.println(integerRedisTemplate.opsForValue().increment(key, 1));
		System.out
				.println(integerRedisTemplate.opsForValue().increment(key, 3));
		System.out
				.println(integerRedisTemplate.opsForValue().increment(key, 6));
		Assert.assertEquals(1L,
				integerRedisTemplate.opsForValue().increment(key, 0)
						.longValue());
	}

	@Test
	public void testRank() {
		Set<String> keys = redisTemplate.keys("actRank_*");
		for (String key : keys) {
			System.out.println(key);
		}
	}

	@Test
	public void testSetExpire() throws InterruptedException {
		redisTemplate.opsForSet().add(key, 1L);
		redisTemplate.expire(key, 5, TimeUnit.SECONDS);
		Assert.assertEquals(1, redisTemplate.opsForSet().size(key).intValue());
		Assert.assertTrue(redisTemplate.opsForSet().isMember(key, 1L));

		Thread.sleep(2000);
		Assert.assertEquals(1, redisTemplate.opsForSet().size(key).intValue());
		Assert.assertTrue(redisTemplate.opsForSet().isMember(key, 1L));

		redisTemplate.opsForSet().add(key, 2L);
		redisTemplate.expire(key, 5, TimeUnit.SECONDS);
		Assert.assertEquals(2, redisTemplate.opsForSet().size(key).intValue());
		Assert.assertTrue(redisTemplate.opsForSet().isMember(key, 2L));
		Assert.assertTrue(redisTemplate.opsForSet().isMember(key, 1L));

		Thread.sleep(6000);
		Assert.assertEquals(0, redisTemplate.opsForSet().size(key).intValue());
	}
}
