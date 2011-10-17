package com.juzhai.core.mail.manager;

import com.juzhai.core.mail.bean.Mail;

public interface MailSender {

	void send(Mail mail) throws Exception;
}
