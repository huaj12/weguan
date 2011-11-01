package com.juzhai.cms.controller.view;

import java.io.Serializable;

import com.juzhai.act.model.Act;

public class CmsActMagerView implements Serializable {
	private static final long serialVersionUID = -1871387836944777684L;

	private Act act;

	private String logoWebPath;

	private String address;

	private String gender;

	private String age;

	private String status;

	private String category;

	private String proName;

	private String cityName;

	public CmsActMagerView(Act act, String logoWebPath, String proName,
			String cityName, String address, String age, String gender,
			String status, String category) {
		this.act = act;
		this.logoWebPath = logoWebPath;
		this.address = address;
		this.category = category;
		this.status = status;
		this.age = age;
		this.gender = gender;
		this.proName = proName;
		this.cityName = cityName;

	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public String getLogoWebPath() {
		return logoWebPath;
	}

	public void setLogoWebPath(String logoWebPath) {
		this.logoWebPath = logoWebPath;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
