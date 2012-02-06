package com.juzhai.cms.controller.view;

import com.juzhai.post.model.Idea;

public class CmsIdeaView {
	private Idea idea;

	private String userName;


	public CmsIdeaView(Idea idea, String userName) {
		this.idea = idea;
		this.userName = userName;
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
