package com.juzhai.cms.controller.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AddActForm {
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private MultipartFile imgFile;
	
	private Boolean checkAddress;

	public Boolean getCheckAddress() {
		return checkAddress;
	}

	public void setCheckAddress(Boolean checkAddress) {
		this.checkAddress = checkAddress;
	}

	private Long id;

	private String name;

	private String fullName;

	private String intro;

	private List<Long>  catIds;

	private Long province;

	private Long city;

	private String suitGender;

	private String suiAge;

	private String address;

	private String suitStatu;

	private Integer minRoleNum;

	private Integer maxRoleNum;

	private String startTime;

	private String endTime;

	private Integer minCharge;

	private Integer maxCharge;



	public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}


	public List<Long> getCatIds() {
		return catIds;
	}

	public void setCatIds(List<Long> catIds) {
		this.catIds = catIds;
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


	public String getSuiAge() {
		return suiAge;
	}

	public void setSuiAge(String suiAge) {
		this.suiAge = suiAge;
	}


	public String getSuitGender() {
		return suitGender;
	}

	public void setSuitGender(String suitGender) {
		this.suitGender = suitGender;
	}

	public String getSuitStatu() {
		return suitStatu;
	}

	public void setSuitStatu(String suitStatu) {
		this.suitStatu = suitStatu;
	}

	public Integer getMinRoleNum() {
		return minRoleNum;
	}

	public void setMinRoleNum(Integer minRoleNum) {
		this.minRoleNum = minRoleNum;
	}

	public Integer getMaxRoleNum() {
		return maxRoleNum;
	}

	public void setMaxRoleNum(Integer maxRoleNum) {
		this.maxRoleNum = maxRoleNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getMinCharge() {
		return minCharge;
	}

	public void setMinCharge(Integer minCharge) {
		this.minCharge = minCharge;
	}

	public Integer getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(Integer maxCharge) {
		this.maxCharge = maxCharge;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
