package com.juzhai.index.controller.view;

import com.juzhai.post.model.Idea;

public class IdeaView {

	private Idea idea;

	private boolean hasUsed;

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
