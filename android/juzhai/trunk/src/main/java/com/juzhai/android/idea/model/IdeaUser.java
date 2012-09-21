package com.juzhai.android.idea.model;

import com.juzhai.android.passport.model.User;

public class IdeaUser {
	private long ideaId;

	private User userView;

	private long createTime;

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public User getUserView() {
		return userView;
	}

	public void setUserView(User userView) {
		this.userView = userView;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
