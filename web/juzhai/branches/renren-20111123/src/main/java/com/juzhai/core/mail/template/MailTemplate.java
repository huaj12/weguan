package com.juzhai.core.mail.template;

import java.util.Map;

public interface MailTemplate {

	String generate(String templetePath, Map<String, Object> props,
			String encoding);
}
