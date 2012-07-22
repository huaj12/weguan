package com.juzhai.lab.controller.view;

public class IdeaUserMView {

	private long ideaId;

	private UserMView userView;

	private long createTime;

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public UserMView getUserView() {
		return userView;
	}

	public void setUserView(UserMView userView) {
		this.userView = userView;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
