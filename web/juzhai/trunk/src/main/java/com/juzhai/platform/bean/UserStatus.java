package com.juzhai.platform.bean;

import java.io.Serializable;
import java.util.Date;

public class UserStatus implements Serializable {

	private static final long serialVersionUID = 6639708910341449357L;

	private String content;
	private Date time;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
