package com.juzhai.cms.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Idea;

public class CmsIdeaView {
	private Idea idea;

	private ProfileCache createUser;

	public CmsIdeaView(Idea idea, ProfileCache createUser) {
		this.idea = idea;
		this.createUser = createUser;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public ProfileCache getCreateUser() {
		return createUser;
	}

	public void setCreateUser(ProfileCache createUser) {
		this.createUser = createUser;
	}
}
