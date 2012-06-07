package com.juzhai.index.bean;

public enum ShowIdeaOrder {

	HOT_TIME("time", "create_time"), POPULARITY("pop", "use_count"), 
	//TODO (review) 移除WINDOW_TIME 这个枚举的设计不是用来做这个用途的。是基于条件完全一样的情况下，不同的排序。推荐是另外一个纬度的内容获取。
	WINDOW_TIME(
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