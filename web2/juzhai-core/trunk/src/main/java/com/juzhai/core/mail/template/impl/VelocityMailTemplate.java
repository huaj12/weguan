package com.juzhai.core.mail.template.impl;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.juzhai.core.mail.template.MailTemplate;

public class VelocityMailTemplate implements MailTemplate {

	private VelocityEngine velocityEngine;

	@Override
	public String generate(String templetePath, Map<String, Object> props,
			String encoding) {
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, templetePath, encoding, props);
		return text;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
}
