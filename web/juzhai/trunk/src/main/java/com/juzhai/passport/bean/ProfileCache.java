package com.juzhai.passport.bean;

import java.io.Serializable;

public class ProfileCache implements Serializable {

	private static final long serialVersionUID = 3187093291974355755L;

	private Long uid;
	private String nickname;
	private String logoPic;
	private Long province;
	private Long city;
	private Integer gender;
	private Integer birthYear;
	private Integer birthMonth;
	private Integer birthDay;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLogoPic() {
		return logoPic;
	}

	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	public Integer getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}

	public Integer getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}
}
