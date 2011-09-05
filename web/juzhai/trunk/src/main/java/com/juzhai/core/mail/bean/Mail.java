package com.juzhai.core.mail.bean;

import java.io.Serializable;

public class Mail implements Serializable {

	private static final long serialVersionUID = -3704848536862469682L;

	private MailUser receiver;

	private MailContent subject;

	private MailContent text;

	private String encoding;

	private boolean html;

	public MailUser getReceiver() {
		return receiver;
	}

	public void setReceiver(MailUser receiver) {
		this.receiver = receiver;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public MailContent getSubject() {
		return subject;
	}

	public void setSubject(MailContent subject) {
		this.subject = subject;
	}

	public MailContent getText() {
		return text;
	}

	public void setText(MailContent text) {
		this.text = text;
	}
}
