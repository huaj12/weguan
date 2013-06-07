package com.easylife.weather.passport.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserConfig implements Serializable {
	private static final long serialVersionUID = 4212106071997534538L;
	private String token;
	private String cityName;
	private boolean remindRain;
	private boolean remindWind;
	private boolean remindCooling;
	private String timeStr;
	private long time;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public boolean isRemindRain() {
		return remindRain;
	}

	public void setRemindRain(boolean remindRain) {
		this.remindRain = remindRain;
	}

	public boolean isRemindWind() {
		return remindWind;
	}

	public void setRemindWind(boolean remindWind) {
		this.remindWind = remindWind;
	}

	public boolean isRemindCooling() {
		return remindCooling;
	}

	public void setRemindCooling(boolean remindCooling) {
		this.remindCooling = remindCooling;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
