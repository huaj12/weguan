package com.juzhai.android.common.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.juzhai.android.core.model.Entity;

public class Province extends Entity {
	private static final long serialVersionUID = -8262674956249966324L;
	private long provinceId;
	private String provinceName;

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

	@Override
	public String toString() {
		return provinceName;
	}

	@Override
	@JsonIgnore
	public Object getIdentify() {
		return provinceId;
	}

}
