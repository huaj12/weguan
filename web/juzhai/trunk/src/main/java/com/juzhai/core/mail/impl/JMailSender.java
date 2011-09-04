package com.juzhai.core.mail.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

import com.juzhai.core.mail.Mail;
import com.juzhai.core.mail.MailSender;
import com.juzhai.core.mail.MailUser;

public class JMailSender implements MailSender {

	private JavaMailSender javaMailSender;

	private MailUser mailUser;

	@Override
	public void send(Mail mail) {
		MimeMessage message = javaMailSender.createMimeMessage();

	}

}
