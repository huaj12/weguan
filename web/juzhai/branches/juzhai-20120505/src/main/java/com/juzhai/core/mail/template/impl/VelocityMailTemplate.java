package com.juzhai.core.mail.template.impl;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.juzhai.core.mail.template.MailTemplate;

@Component
public class VelocityMailTemplate implements MailTemplate {

	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	public String generate(String templetePath, Map<String, Object> props,
			String encoding) {
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, templetePath, encoding, props);
		return text;
	}
}
