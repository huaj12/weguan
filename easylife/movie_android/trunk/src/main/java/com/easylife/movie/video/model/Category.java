package com.easylife.movie.video.model;

public class Category {
	private String name;
	private long categoryId;

	public Category(long categoryId) {
		super();
		this.categoryId = categoryId;
	}

	public Category() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (((Category) o).getCategoryId() == this.categoryId) {
			return true;
		} else {
			return false;
		}
	}
}
