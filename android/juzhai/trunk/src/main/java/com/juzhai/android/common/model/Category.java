package com.juzhai.android.common.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.juzhai.android.core.model.Entity;

public class Category extends Entity {
	private static final long serialVersionUID = 7440601412026137403L;
	private long categoryId;
	private String name;
	private String icon;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	@JsonIgnore
	public Object getIdentify() {
		return categoryId;
	}

}
