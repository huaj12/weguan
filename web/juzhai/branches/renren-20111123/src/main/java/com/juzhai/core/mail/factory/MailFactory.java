package com.juzhai.core.mail.factory;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.bean.MailContent;
import com.juzhai.core.mail.bean.MailUser;


public class MailFactory {

	public static Mail create(String receiverEmail, String receiverName) {
		return create(receiverEmail, receiverName, null, false);
	}

	public static Mail create(String receiverEmail, String receiverName,
			boolean isHtml) {
		return create(receiverEmail, receiverName, null, isHtml);
	}

	public static Mail create(String receiverEmail, String receiverName,
			String encoding, boolean isHtml) {
		Mail mail = new Mail();
		mail.setReceiver(new MailUser(receiverEmail, receiverName));
		mail.setSubject(new MailContent());
		mail.setText(new MailContent());
		mail.setEncoding(encoding);
		mail.setHtml(isHtml);
		return mail;
	}
}
