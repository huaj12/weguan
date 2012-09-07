/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.amqp.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class RabbitMQTest {

	@Autowired
	private AmqpTemplate actMsgRabbitTemplate;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// @Test
	// public void simpleTest() throws TimeoutException, InterruptedException,
	// MemcachedException {
	// MsgMessage<ActMsg> msgMessage = new MsgMessage<ActMsg>();
	// msgMessage.buildReceiverId(10L);
	// actMsgRabbitTemplate.convertAndSend(msgMessage);
	// Thread.sleep(300000000);
	// }

	@Test
	public void benchmarkTest() {

	}
}
