package com.juzhai.mobile.common.controller.view;

import com.juzhai.post.model.Category;

public class CategoryMView {

	private long categoryId;
	private String name;
	private String icon;

	public CategoryMView(Category category) {
		this.categoryId = category.getId();
		this.name = category.getName();
		this.icon = category.getIcon();
	}

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
}
