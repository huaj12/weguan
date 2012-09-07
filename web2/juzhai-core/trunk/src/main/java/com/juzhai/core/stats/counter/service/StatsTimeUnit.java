package com.juzhai.core.stats.counter.service;

import java.util.Calendar;

public enum StatsTimeUnit {
	MINUTE(Calendar.MINUTE), HOUR(Calendar.HOUR), DATE(Calendar.DATE), MONTH(
			Calendar.MONTH), YEAR(Calendar.YEAR), NONE(null);

	private Integer field;

	private StatsTimeUnit(Integer field) {
		this.field = field;
	}

	public Integer getField() {
		return field;
	}
}
