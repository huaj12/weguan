package com.android.weatherspider.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserConfig implements Serializable {
	private static final long serialVersionUID = -5798857747690898151L;
	private String token;
	private String cityName;
	private int city;
	// 设备类型
	private String deviceName;
	private boolean remindRain;
	private boolean remindWind;
	private boolean remindCooling;
	private Integer hour;

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

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

}
