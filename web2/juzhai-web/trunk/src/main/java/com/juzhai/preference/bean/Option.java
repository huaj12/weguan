package com.juzhai.preference.bean;

import java.io.Serializable;

public class Option implements Serializable {

	private static final long serialVersionUID = -7494278922604789180L;

	private String name;
	private Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
