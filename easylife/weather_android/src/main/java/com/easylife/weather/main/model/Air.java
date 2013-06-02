package com.easylife.weather.main.model;

public class Air {
	private String title;// 标题（实时空气污染指数）
	private String cityaveragename;// 城市平均标题（上海全城平均）
	private String level;// 空气指数
	private String pm25;// 空气参数
	private String pm10;// 空气参数
	private String so2;// 空气参数
	private String co;// 空气参数
	private String no2;// 空气参数
	private String o3;// 空气参数
	private String aqigrade;// 空气级别 优 差
	private String desc;// 空气参数
	private String updateTime;// 发布时间
	private String hname;// pm最大的地点名字
	private String hiaqi;// pm指数
	private String lname;// pm最小的地点名字
	private String liaqi;// pm指数

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCityaveragename() {
		return cityaveragename;
	}

	public void setCityaveragename(String cityaveragename) {
		this.cityaveragename = cityaveragename;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getSo2() {
		return so2;
	}

	public void setSo2(String so2) {
		this.so2 = so2;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public String getAqigrade() {
		return aqigrade;
	}

	public void setAqigrade(String aqigrade) {
		this.aqigrade = aqigrade;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
	}

	public String getHiaqi() {
		return hiaqi;
	}

	public void setHiaqi(String hiaqi) {
		this.hiaqi = hiaqi;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLiaqi() {
		return liaqi;
	}

	public void setLiaqi(String liaqi) {
		this.liaqi = liaqi;
	}

}
