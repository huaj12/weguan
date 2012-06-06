package com.juzhai.index.bean;

public enum ShowIdeaOrder {

	HOT_TIME("time", "create_time"), POPULARITY("pop", "use_count"), WINDOW_TIME(
			"window", "create_window_time");

	private String type;

	private String column;

	private ShowIdeaOrder(String type, String column) {
		this.type = type;
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public String getType() {
		return type;
	}

	public static ShowIdeaOrder getShowIdeaOrderByType(String orderType) {
		for (ShowIdeaOrder order : values()) {
			if (order.getType().equals(orderType)) {
				return order;
			}
		}
		return null;
	}
}