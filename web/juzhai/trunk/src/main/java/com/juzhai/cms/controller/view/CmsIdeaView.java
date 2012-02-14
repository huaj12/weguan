package com.juzhai.cms.controller.view;

import com.juzhai.post.model.Idea;

public class CmsIdeaView {
	private Idea idea;

	private String userName;

	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CmsIdeaView(Idea idea, String userName, String categoryName) {
		this.idea = idea;
		this.userName = userName;
		this.categoryName = categoryName;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
