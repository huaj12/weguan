package com.juzhai.lab.controller.view;

public class UserHomeMView {

	private long uid;
	private String nickname;
	private int gender;
	private String logo;
	private int interestUserCount;
	private int interestMeCount;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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
}
