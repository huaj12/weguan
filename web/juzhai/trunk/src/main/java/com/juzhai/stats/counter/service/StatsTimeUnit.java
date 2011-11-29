package com.juzhai.stats.counter.service;

import java.util.Calendar;

public enum StatsTimeUnit {
	MINUTE(Calendar.MINUTE), HOUR(Calendar.HOUR), DATE(Calendar.DATE), MONTH(
			Calendar.MONTH), YEAR(Calendar.YEAR);

	private int field;

	private StatsTimeUnit(int field) {
		this.field = field;
	}

	public int getField() {
		return field;
	}
}
