package com.juzhai.home.controller.form;

import java.util.Calendar;
import java.util.Date;

public class DateView {

	private Date date;
	private boolean free;
	private int dayOfTheWeek;

	public DateView(Date date, boolean free) {
		this.date = date;
		this.free = free;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		this.dayOfTheWeek = c.get(Calendar.DAY_OF_WEEK);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public int getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(int dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}
}
