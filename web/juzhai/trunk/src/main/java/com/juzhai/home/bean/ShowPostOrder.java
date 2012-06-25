package com.juzhai.home.bean;

public enum ShowPostOrder {
	NEW("new", "last_update_time"), ONLINE("online", "last_web_login_time");

	private String type;

	private String column;

	private ShowPostOrder(String type, String column) {
		this.type = type;
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public String getType() {
		return type;
	}

	public static ShowPostOrder getShowPostOrderByType(String orderType) {
		for (ShowPostOrder order : values()) {
			if (order.getType().equals(orderType)) {
				return order;
			}
		}
		return null;
	}
}
