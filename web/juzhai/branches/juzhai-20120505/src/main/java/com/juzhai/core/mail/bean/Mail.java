package com.juzhai.core.mail.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.juzhai.core.Constants;

public class Mail implements Serializable {

	private static final long serialVersionUID = -3704848536862469682L;

	private static final String DEFAULT_ENCODING = Constants.UTF8;

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
		return StringUtils.isEmpty(encoding) ? DEFAULT_ENCODING : encoding;
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

	public void buildSubject(String templatePath, Map<String, Object> props) {
		if (null == this.subject) {
			this.subject = new MailContent();
		}
		this.subject.setTemplatePath(templatePath);
		if (null == props) {
			props = new HashMap<String, Object>();
		}
		props.put("nickname", this.receiver.getNickname());
		this.subject.setProp(props);
	}

	public void buildText(String templatePath, Map<String, Object> props) {
		if (null == this.text) {
			this.text = new MailContent();
		}
		this.text.setTemplatePath(templatePath);
		if (null == props) {
			props = new HashMap<String, Object>();
		}
		props.put("nickname", this.receiver.getNickname());
		this.text.setProp(props);
	}
}
