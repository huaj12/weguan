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

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.mail.bean.Mail;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.service.IInboxService;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class RedisTest {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, Integer> integerRedisTemplate;
	@Autowired
	private RedisTemplate<String, Mail> mailRedisTemplate;
	@Autowired
	private RedisTemplate<String, ActMsg> actMsgRedisTemplate;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInboxService inboxService;
	private String key = "simpleKey";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {

		redisTemplate.delete(key);
		actMsgRedisTemplate.delete(key);
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

	@Test
	public void listRemovePojoTest() {
		ActMsg value1 = new ActMsg(1L, 1L, MsgType.INVITE);
		ActMsg value2 = new ActMsg(2L, 2L, MsgType.INVITE);
		ActMsg value3 = new ActMsg(3L, 3L, MsgType.INVITE);
		ActMsg value4 = new ActMsg(4L, 4L, MsgType.INVITE);
		ActMsg value5 = new ActMsg(1L, 1L, MsgType.INVITE);
		ActMsg value6 = new ActMsg(2L, 2L, MsgType.INVITE);
		actMsgRedisTemplate.opsForList().leftPush(key, value1);
		actMsgRedisTemplate.opsForList().leftPush(key, value2);
		actMsgRedisTemplate.opsForList().leftPush(key, value3);
		actMsgRedisTemplate.opsForList().leftPush(key, value4);
		actMsgRedisTemplate.opsForList().leftPush(key, value5);
		actMsgRedisTemplate.opsForList().leftPush(key, value6);

		ActMsg removePojo = actMsgRedisTemplate.opsForList().index(key, 1);
		System.out.println(removePojo.getUid());
		System.out.println(removePojo.getActId());
		System.out.println("**************************");

		actMsgRedisTemplate.opsForList().remove(key, 0, removePojo);
		List<ActMsg> returnValues = actMsgRedisTemplate.opsForList().range(key,
				0, -1);
		for (ActMsg value : returnValues) {
			System.out.println(value.getUid());
			System.out.println(value.getActId());
			System.out.println("---------------------------");
		}
	}

	@Test
	public void emailQueue() {
		while (true) {
			Mail mail = mailRedisTemplate.opsForList().leftPop(
					RedisKeyGenerator.genMailQueueKey(), 5, TimeUnit.SECONDS);
			System.out.println(mail.getReceiver().getEmailAddress());
		}
	}

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
	public void testMyActs() {
		System.out.println(redisTemplate.opsForZSet().size(
				RedisKeyGenerator.genMyActsKey(12)));
		Feed feed = inboxService.showRecommend(12);
		System.out.println(feed.getAct().getId());
	}
}
