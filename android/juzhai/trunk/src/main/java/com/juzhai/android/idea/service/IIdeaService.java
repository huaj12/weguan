package com.juzhai.android.idea.service;

import android.content.Context;

import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.idea.exception.IdeaException;

public interface IIdeaService {
	IdeaListResult list(Context context, long categoryId, String orderType,
			int page) throws IdeaException;

	IdeaUserListResult listIdeaUser(Context context, long ideaId, int page)
			throws IdeaException;

}
