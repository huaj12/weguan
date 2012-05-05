package com.juzhai.index.controller.view;

import com.juzhai.post.model.Category;

public class CategoryView {

	private Category category;

	private int ideaCount;

	public CategoryView(Category category, int ideaCount) {
		super();
		this.category = category;
		this.ideaCount = ideaCount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getIdeaCount() {
		return ideaCount;
	}

	public void setIdeaCount(int ideaCount) {
		this.ideaCount = ideaCount;
	}
}
