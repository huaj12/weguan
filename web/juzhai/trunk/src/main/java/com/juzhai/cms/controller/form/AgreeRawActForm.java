package com.juzhai.cms.controller.form;

public class AgreeRawActForm {
	private long id;
	private long town;
	private long province;
	private long city;
	private String name;
	private String detail;
	private String logo;
	// TODO (review) 如果是多个分类，那用long是不够的；如果是一个分类，那命名是不规范的
	private long categoryIds;
	private String address;
	private String startTime;
	private String endTime;
	private long createUid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTown() {
		return town;
	}

	public void setTown(long town) {
		this.town = town;
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

	public long getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(long categoryIds) {
		this.categoryIds = categoryIds;
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
