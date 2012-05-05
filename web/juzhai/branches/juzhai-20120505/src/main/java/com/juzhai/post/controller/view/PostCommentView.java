package com.juzhai.post.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.post.model.PostComment;

public class PostCommentView {

	private PostComment postComment;

	private ProfileCache createUser;

	private ProfileCache parentUser;

	private ProfileCache postCreateUser;

	public PostComment getPostComment() {
		return postComment;
	}

	public void setPostComment(PostComment postComment) {
		this.postComment = postComment;
	}

	public ProfileCache getCreateUser() {
		return createUser;
	}

	public void setCreateUser(ProfileCache createUser) {
		this.createUser = createUser;
	}

	public ProfileCache getParentUser() {
		return parentUser;
	}

	public void setParentUser(ProfileCache parentUser) {
		this.parentUser = parentUser;
	}

	public ProfileCache getPostCreateUser() {
		return postCreateUser;
	}

	public void setPostCreateUser(ProfileCache postCreateUser) {
		this.postCreateUser = postCreateUser;
	}
}
