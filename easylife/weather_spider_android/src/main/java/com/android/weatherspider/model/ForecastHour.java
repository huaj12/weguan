package com.android.weatherspider.model;

public class ForecastHour {
	private String id;// 可以用于时间段排序的 id从小到大
	private String timeInterval;// 时间段 12:00-18:00
	private String sky;// 天气 阴天 雨天 晴天
	private String minTmp;// 最小温度
	private String maxTmp;// 最大温度
	private String windDirection;// 风向
	private String windPower;// 风力

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMinTmp() {
		return minTmp;
	}

	public void setMinTmp(String minTmp) {
		this.minTmp = minTmp;
	}

	public String getMaxTmp() {
		return maxTmp;
	}

	public void setMaxTmp(String maxTmp) {
		this.maxTmp = maxTmp;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindPower() {
		return windPower;
	}

	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}

}
