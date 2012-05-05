package com.juzhai.cms.controller.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AddActForm {

	private Long addUid;

	private Long id;

	private String name;

	private String fullName;

	private String intro;

	private String detail;

	private List<Long> catIds;

	private Long province;

	private Long city;

	private Long town;

	private String address;

	private String suitGender;

	private String suitAge;

	private String suitStatus;

	private Integer minRoleNum;

	private Integer maxRoleNum;

	private String startTime;

	private String endTime;

	private Integer minCharge;

	private Integer maxCharge;

	private String keyWords;

	private MultipartFile imgFile;

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

	public Long getTown() {
		return town;
	}

	public void setTown(Long town) {
		this.town = town;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getSuitGender() {
		return suitGender;
	}

	public void setSuitGender(String suitGender) {
		this.suitGender = suitGender;
	}

	public String getSuitAge() {
		return suitAge;
	}

	public void setSuitAge(String suitAge) {
		this.suitAge = suitAge;
	}

	public String getSuitStatus() {
		return suitStatus;
	}

	public void setSuitStatus(String suitStatus) {
		this.suitStatus = suitStatus;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getAddUid() {
		return addUid;
	}

	public void setAddUid(Long addUid) {
		this.addUid = addUid;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

}
