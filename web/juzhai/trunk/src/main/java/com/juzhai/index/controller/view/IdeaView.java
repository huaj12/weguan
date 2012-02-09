package com.juzhai.index.controller.view;

import java.util.List;

import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.model.Idea;

public class IdeaView {

	private Idea idea;

	private boolean hasUsed;

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

}
