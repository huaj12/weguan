package com.juzhai.lab.controller.view;

public class UserMView {

	private long uid;
	private String nickname;
	private int gender;
	private String logo;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	private String constellation;
	private String profession;
	private String cityName;
	private String townName;
	private String feature;
	private int interestUserCount;
	private int interestMeCount;
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

}
