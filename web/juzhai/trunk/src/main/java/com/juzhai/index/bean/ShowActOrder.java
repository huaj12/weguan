package com.juzhai.index.bean;

public enum ShowActOrder {

	HOT_TIME("time", "hot_create_time"), POPULARITY("pop", "recent_popularity");

	private String type;

	private String column;

	private ShowActOrder(String type, String column) {
		this.type = type;
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public String getType() {
		return type;
	}

	public static ShowActOrder getShowActOrderByType(String orderType) {
		for (ShowActOrder order : values()) {
			if (order.getType().equals(orderType)) {
				return order;
			}
		}
		return null;
	}
}