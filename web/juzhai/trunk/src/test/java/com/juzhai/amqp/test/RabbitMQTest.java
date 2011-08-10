/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.amqp.test;

import java.util.concurrent.TimeoutException;

import junit.framework.Assert;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.act.model.UserAct;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class RabbitMQTest {

	@Autowired
	private AmqpTemplate updateActRabbitTemplate;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void simpleTest() throws TimeoutException, InterruptedException,
			MemcachedException {
		UserAct userAct=new UserAct();
		userAct.setUid(10L);
		updateActRabbitTemplate.convertAndSend(userAct);
	}

	@Test
	public void benchmarkTest() {

	}
}
