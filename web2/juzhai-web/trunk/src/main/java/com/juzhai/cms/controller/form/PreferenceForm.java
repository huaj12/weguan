package com.juzhai.cms.controller.form;

import com.juzhai.preference.bean.Input;

public class PreferenceForm {
	private Long id;
	private String name;
	private Input input;
	private String inputString;
	private Integer type;
	private boolean open;
	private Integer sequence;
	private String[] defaultAnswer;
	private boolean openDescription;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getInputString() {
		return inputString;
	}

	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String[] getDefaultAnswer() {
		return defaultAnswer;
	}

	public void setDefaultAnswer(String[] defaultAnswer) {
		this.defaultAnswer = defaultAnswer;
	}

	public boolean getOpenDescription() {
		return openDescription;
	}

	public void setOpenDescription(boolean openDescription) {
		this.openDescription = openDescription;
	}

}
