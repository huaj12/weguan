package com.android.weatherspider.model;

import java.util.List;

public class WeatherInfo {

	private String daytimeSky;// 白天天气 阴天 雨天 晴天
	private String nightSky;// 晚上天气 阴天 雨天 晴天
	private String date;// 时间 2013/03/29
	private String description;// 天气描述 厚厚云层笼罩下的低温天气，外出请注意保暖
	private String windDirection;// 风向
	private String windPower;// 风力
	private String minTmp;// 最小气温
	private String maxTmp;// 最大气温
	private String sunrise;// 日出时间
	private String sunset;// 日落时间
	private String lastFestival;// 距离最近的节日1|愚人节;1(前面一个1不知道什么意思.看|后面的数据 距离愚人节还有
								// 1天)
	private String daytimeWindPower;// 白天风力
	private String nightWindPower;// 晚上风力
	private String daytimeWindDirection;// 白天风向
	private String nightWindDirection;// 晚上风向
	private String sky; // 天气
	private Air air;//
	private String lunarCalendar;// 农历日期癸巳年二月十八
	private String updateTime;// 发布时间
	private String nowTmp;// 实时温度
	private String hum;// 湿度
	private List<ForecastHour> forecastHours;

	public String getDaytimeSky() {
		return daytimeSky;
	}

	public void setDaytimeSky(String daytimeSky) {
		this.daytimeSky = daytimeSky;
	}

	public String getNightSky() {
		return nightSky;
	}

	public void setNightSky(String nightSky) {
		this.nightSky = nightSky;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getLastFestival() {
		return lastFestival;
	}

	public void setLastFestival(String lastFestival) {
		this.lastFestival = lastFestival;
	}

	public String getDaytimeWindPower() {
		return daytimeWindPower;
	}

	public void setDaytimeWindPower(String daytimeWindPower) {
		this.daytimeWindPower = daytimeWindPower;
	}

	public String getNightWindPower() {
		return nightWindPower;
	}

	public void setNightWindPower(String nightWindPower) {
		this.nightWindPower = nightWindPower;
	}

	public String getDaytimeWindDirection() {
		return daytimeWindDirection;
	}

	public void setDaytimeWindDirection(String daytimeWindDirection) {
		this.daytimeWindDirection = daytimeWindDirection;
	}

	public String getNightWindDirection() {
		return nightWindDirection;
	}

	public void setNightWindDirection(String nightWindDirection) {
		this.nightWindDirection = nightWindDirection;
	}

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public Air getAir() {
		return air;
	}

	public void setAir(Air air) {
		this.air = air;
	}

	public List<ForecastHour> getForecastHours() {
		return forecastHours;
	}

	public void setForecastHours(List<ForecastHour> forecastHours) {
		this.forecastHours = forecastHours;
	}

	public String getLunarCalendar() {
		return lunarCalendar;
	}

	public void setLunarCalendar(String lunarCalendar) {
		this.lunarCalendar = lunarCalendar;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getNowTmp() {
		return nowTmp;
	}

	public void setNowTmp(String nowTmp) {
		this.nowTmp = nowTmp;
	}

	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

}
