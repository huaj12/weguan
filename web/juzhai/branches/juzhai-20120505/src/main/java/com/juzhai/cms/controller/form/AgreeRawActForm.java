package com.juzhai.cms.controller.form;

public class AgreeRawActForm {
	private long rawActId;
	private long town;
	private long province;
	private long city;
	private String name;
	private String detail;
	private String logo;
	private long categoryId;
	private String address;
	private String startTime;
	private String endTime;
	private long createUid;


	public long getRawActId() {
		return rawActId;
	}

	public void setRawActId(long rawActId) {
		this.rawActId = rawActId;
	}

	public long getTown() {
		return town;
	}

	public void setTown(long town) {
		this.town = town;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getProvince() {
		return province;
	}

	public void setProvince(long province) {
		this.province = province;
	}

	public long getCity() {
		return city;
	}

	public void setCity(long city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public long getCreateUid() {
		return createUid;
	}

	public void setCreateUid(long createUid) {
		this.createUid = createUid;
	}

}
