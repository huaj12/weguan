package com.juzhai.android.core.model;

import java.util.List;

import com.juzhai.android.common.model.Category;
import com.juzhai.android.dialog.model.Dialog;
import com.juzhai.android.dialog.model.DialogContent;
import com.juzhai.android.idea.model.Idea;
import com.juzhai.android.idea.model.IdeaUser;
import com.juzhai.android.passport.model.Post;
import com.juzhai.android.passport.model.User;

public class Result<T> {
	private T result;
	private String errorCode;
	private Boolean success;
	private String errorInfo;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public static class UserResult extends Result<User> {
	}

	public static class StringResult extends Result<String> {
	}

	public static class IdeaListResult extends Result<PageList<Idea>> {
	}

	public static class IdeaUserListResult extends Result<PageList<IdeaUser>> {
	}

	public static class CategoryResult extends Result<List<Category>> {
	}

	public static class DialogListResult extends Result<PageList<Dialog>> {
	}

	public static class DialogContentListResult extends
			Result<PageList<DialogContent>> {
	}

	public static class DialogContentResult extends Result<DialogContent> {
	}

	public static class UserListResult extends Result<PageList<User>> {
	}

	public static class PostListResult extends Result<PageList<Post>> {
	}

}
