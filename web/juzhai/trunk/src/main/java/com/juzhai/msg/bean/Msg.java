package com.juzhai.msg.bean;

import java.io.Serializable;
import java.util.Date;

public class Msg implements Serializable {

	private static final long serialVersionUID = -7522547576950402686L;

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
