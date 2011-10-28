package com.juzhai.act.controller.view;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;

public class CategoryActView {

	private Category category;

	private Act act;

	private boolean hasUsed;

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public boolean isHasUsed() {
		return hasUsed;
	}

	public void setHasUsed(boolean hasUsed) {
		this.hasUsed = hasUsed;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
