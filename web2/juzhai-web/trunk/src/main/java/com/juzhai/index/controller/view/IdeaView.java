package com.juzhai.index.controller.view;

import java.util.List;

import com.juzhai.idea.controller.view.IdeaUserView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.Idea;

public class IdeaView {

	private Idea idea;

	private ProfileCache profileCache;

	private boolean hasUsed;

	private boolean hasInterest;

	private List<IdeaUserView> ideaUserViews;

	public List<IdeaUserView> getIdeaUserViews() {
		return ideaUserViews;
	}

	public void setIdeaUserViews(List<IdeaUserView> ideaUserViews) {
		this.ideaUserViews = ideaUserViews;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public boolean isHasUsed() {
		return hasUsed;
	}

	public void setHasUsed(boolean hasUsed) {
		this.hasUsed = hasUsed;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public boolean isHasInterest() {
		return hasInterest;
	}

	public void setHasInterest(boolean hasInterest) {
		this.hasInterest = hasInterest;
	}

}
