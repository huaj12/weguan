package com.juzhai.mail.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.factory.MailFactory;
import com.juzhai.core.mail.manager.MailManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/application-context.xml" })
public class SendMailTest {

	@Autowired
	private MailManager mailManager;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private void sendMail(String email) throws InterruptedException {
		Mail mail = MailFactory.create(email, "wjj", true);
		mail.buildSubject("/mail/getback/subject.vm", null);
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("code", "abcdefg");
		mail.buildText("/mail/getback/content.vm", props);
		mailManager.sendMail(mail, true);
		Thread.sleep(10000);
	}

	@Test
	public void hotmailTest() throws InterruptedException {
		sendMail("wujiajun_1020@hotmail.com");
	}

	@Test
	public void gmailTest() throws InterruptedException {
		sendMail("wujiajun1020@gmail.com");
	}

	@Test
	public void sinacomTest() throws InterruptedException {
		sendMail("wujiajun1020@sina.com");
	}
}
