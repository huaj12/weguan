package com.juzhai.core.mail.manager.impl;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.bean.MailUser;
import com.juzhai.core.mail.manager.MailSender;
import com.juzhai.core.mail.template.MailTemplate;

public class JMailSender implements MailSender {

	private JavaMailSender javaMailSender;

	private MailUser sender;

	private MailTemplate mailTemplate;

	@Override
	public void send(Mail mail) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,
				mail.getEncoding());
		helper.setTo(mail.getReceiver().getEmailAddress());
		// helper.setTo(mail.getReceiver().getFriendlyEmailAddress());
		helper.setFrom(sender.getEmailAddress(), sender.getNickname());
		helper.setSentDate(new Date());
		helper.setSubject(mail.getSubject().getText(mailTemplate,
				mail.getEncoding()));
		helper.setText(
				mail.getText().getText(mailTemplate, mail.getEncoding()),
				mail.isHtml());

		javaMailSender.send(message);
	}

	public void setSender(MailUser sender) {
		this.sender = sender;
	}

	public void setMailTemplate(MailTemplate mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
}
