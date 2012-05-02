package com.juzhai.search;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.search.bean.Education;

@Component("searchInitData")
@Lazy(false)
public class InitData {

	@Autowired
	private MessageSource messageSource;

	public static final Map<Integer, String> EDUCATION_MAP = new HashMap<Integer, String>();

	@PostConstruct
	public void init() throws JsonGenerationException {
		initEducation();
	}

	private void initEducation() {
		for (Education edu : Education.values()) {
			EDUCATION_MAP.put(edu.getType(), messageSource.getMessage(
					edu.getText(), null, Locale.SIMPLIFIED_CHINESE));
		}

	}

}
