package com.juzhai.android.common.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.core.utils.TextTruncateUtil;

public class City extends Entity {
	private static final long serialVersionUID = -2080290528597355764L;
	private long provinceId;
	private long cityId;
	private String cityName;

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return TextTruncateUtil.truncate(cityName, 10, "");
	}

	@Override
	@JsonIgnore
	public Object getIdentify() {
		return cityId;
	}

}
