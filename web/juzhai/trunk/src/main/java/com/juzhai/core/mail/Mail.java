package com.juzhai.core.mail;

import java.io.Serializable;

public class Mail implements Serializable {

	private static final long serialVersionUID = -3704848536862469682L;

	private MailUser receiver;

	private MailSender sender;

	private String subject;

	private String content;

	private String encoding;

	public MailUser getReceiver() {
		return receiver;
	}

	public void setReceiver(MailUser receiver) {
		this.receiver = receiver;
	}

	public MailSender getSender() {
		return sender;
	}

	public void setSender(MailSender sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
