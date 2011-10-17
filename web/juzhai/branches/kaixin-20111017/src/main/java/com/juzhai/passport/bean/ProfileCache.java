package com.juzhai.passport.bean;

import java.io.Serializable;

import com.juzhai.passport.InitData;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;

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
	private String email;
	private Boolean subEmail;

	private String tpIdentity;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getSubEmail() {
		return subEmail;
	}

	public void setSubEmail(Boolean subEmail) {
		this.subEmail = subEmail;
	}

	public String getTpIdentity() {
		return tpIdentity;
	}

	public void setTpIdentity(String tpIdentity) {
		this.tpIdentity = tpIdentity;
	}

	public String getProvinceName() {
		if (null == province) {
			return null;
		} else {
			Province provinceObj = InitData.PROVINCE_MAP.get(province);
			return null == provinceObj ? null : provinceObj.getName();
		}
	}

	public String getCityName() {
		if (null == city) {
			return null;
		} else {
			City cityObj = InitData.CITY_MAP.get(city);
			return null == cityObj ? null : cityObj.getName();
		}
	}
}
