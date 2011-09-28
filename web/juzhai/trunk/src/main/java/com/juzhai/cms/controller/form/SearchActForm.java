package com.juzhai.cms.controller.form;

public class SearchActForm {

	private String startDate;

	private String endDate;

	/**
	 * 0.热度 1.时间
	 */
	private int order;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
