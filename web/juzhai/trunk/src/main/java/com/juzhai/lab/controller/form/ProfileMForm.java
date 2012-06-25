package com.juzhai.lab.controller.form;

import org.springframework.web.multipart.MultipartFile;

public class ProfileMForm {

	private String nickname;
	private String birth;
	private String feature;
	private long professionId;
	private String profession;
	private MultipartFile logo;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
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

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}
}
