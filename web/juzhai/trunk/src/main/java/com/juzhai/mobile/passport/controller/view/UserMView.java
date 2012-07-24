package com.juzhai.mobile.passport.controller.view;

import com.juzhai.mobile.post.controller.view.PostMView;

public class UserMView {

	private long uid;
	private String nickname;
	private int gender;
	private String logo;
	private String smallLogo;
	private String bigLogo;
	private String newLogo;
	private int logoVerifyState;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	private String constellation;
	private long professionId;
	private String profession;
	private long provinceId;
	private String provinceName;
	private long cityId;
	private String cityName;
	private long townId;
	private String townName;
	private String feature;
	private int interestUserCount;
	private int interestMeCount;
	private int postCount;
	private boolean hasGuided;
	private boolean hasInterest;
	private PostMView postView;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSmallLogo() {
		return smallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}

	public String getBigLogo() {
		return bigLogo;
	}

	public void setBigLogo(String bigLogo) {
		this.bigLogo = bigLogo;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public int getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public PostMView getPostView() {
		return postView;
	}

	public void setPostView(PostMView postView) {
		this.postView = postView;
	}

	public int getInterestUserCount() {
		return interestUserCount;
	}

	public void setInterestUserCount(int interestUserCount) {
		this.interestUserCount = interestUserCount;
	}

	public int getInterestMeCount() {
		return interestMeCount;
	}

	public void setInterestMeCount(int interestMeCount) {
		this.interestMeCount = interestMeCount;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public long getProfessionId() {
		return professionId;
	}

	public void setProfessionId(long professionId) {
		this.professionId = professionId;
	}

	public String getNewLogo() {
		return newLogo;
	}

	public void setNewLogo(String newLogo) {
		this.newLogo = newLogo;
	}

	public int getLogoVerifyState() {
		return logoVerifyState;
	}

	public void setLogoVerifyState(int logoVerifyState) {
		this.logoVerifyState = logoVerifyState;
	}

	public boolean isHasGuided() {
		return hasGuided;
	}

	public void setHasGuided(boolean hasGuided) {
		this.hasGuided = hasGuided;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}
}
