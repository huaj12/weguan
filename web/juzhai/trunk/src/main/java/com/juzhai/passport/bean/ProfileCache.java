package com.juzhai.passport.bean;

import java.io.Serializable;

import com.juzhai.common.InitData;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;

public class ProfileCache implements Serializable {

	private static final long serialVersionUID = 3187093291974355755L;

	private Long uid;
	private String nickname;
	private String logoPic;
	private Long province;
	private Long city;
	private Long town;
	private Integer gender;
	private Integer birthYear;
	private Integer birthMonth;
	private Integer birthDay;
	private Boolean birthSecret;
	private String email;
	private Boolean subEmail;
	private Long professionId;
	private String profession;
	private Long constellationId;
	private String feature;
	private Integer height;
	private String bloodType;
	private String education;
	private String house;
	private String car;
	private String home;
	private Integer minMonthlyIncome;
	private Integer maxMonthlyIncome;
	private String blog;
	private String newLogoPic;
	private int logoVerifyState;

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

	public Long getTown() {
		return town;
	}

	public void setTown(Long town) {
		this.town = town;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Long getConstellationId() {
		return constellationId;
	}

	public void setConstellationId(Long constellationId) {
		this.constellationId = constellationId;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Boolean getBirthSecret() {
		return birthSecret;
	}

	public void setBirthSecret(Boolean birthSecret) {
		this.birthSecret = birthSecret;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Integer getMinMonthlyIncome() {
		return minMonthlyIncome;
	}

	public void setMinMonthlyIncome(Integer minMonthlyIncome) {
		this.minMonthlyIncome = minMonthlyIncome;
	}

	public Integer getMaxMonthlyIncome() {
		return maxMonthlyIncome;
	}

	public void setMaxMonthlyIncome(Integer maxMonthlyIncome) {
		this.maxMonthlyIncome = maxMonthlyIncome;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getNewLogoPic() {
		return newLogoPic;
	}

	public void setNewLogoPic(String newLogoPic) {
		this.newLogoPic = newLogoPic;
	}

	public int getLogoVerifyState() {
		return logoVerifyState;
	}

	public void setLogoVerifyState(int logoVerifyState) {
		this.logoVerifyState = logoVerifyState;
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

	public Long getProfessionId() {
		return professionId;
	}

	public void setProfessionId(Long professionId) {
		this.professionId = professionId;
	}
}
