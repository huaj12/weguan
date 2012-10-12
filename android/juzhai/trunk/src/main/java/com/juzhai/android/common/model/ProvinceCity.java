package com.juzhai.android.common.model;

import java.util.List;

public class ProvinceCity {
	private List<Province> provinceList;
	private List<City> cityList;

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

}
