package com.juzhai.android.idea.model;

import com.juzhai.android.core.model.Entity;
import com.juzhai.android.passport.model.User;

public class IdeaUser extends Entity {

	private static final long serialVersionUID = -4043146830668132620L;

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

	@Override
	public Object getIdentify() {
		return this.getUserView().getIdentify();
	}

}
