package com.juzhai.core.mail.bean;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.juzhai.core.mail.template.MailTemplate;

public class MailContent implements Serializable {

	private static final long serialVersionUID = 6046971842876871354L;

	private String content;

	private String templatePath;

	private Map<String, Object> props;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProp(Map<String, Object> props) {
		this.props = props;
	}

	public String getText(MailTemplate mailTemplate, String encoding) {
		if (StringUtils.isEmpty(this.content)
				&& StringUtils.isNotEmpty(this.templatePath)) {
			return mailTemplate.generate(this.templatePath, this.props,
					encoding);
		}
		return this.content == null ? "" : this.content;
	}
}
