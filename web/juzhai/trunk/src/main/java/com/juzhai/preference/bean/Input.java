package com.juzhai.preference.bean;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;

import com.juzhai.core.util.JackSonSerializer;

public class Input implements Serializable {

	private static final long serialVersionUID = 6621125063458015025L;

	private List<Option> options;
	private Integer inputType;

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Integer getInputType() {
		return inputType;
	}

	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}

	public String toJsonString() throws JsonGenerationException {
		return JackSonSerializer.toString(this);
	}

	public static Input convertToBean(String jsonAsString)
			throws JsonGenerationException {
		return JackSonSerializer.toBean(jsonAsString, Input.class);
	}
}
