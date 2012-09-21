package com.juzhai.android.idea.service;

import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.idea.exception.IdeaException;

public interface IIdeaService {
	IdeaListResult list(int categoryId, String orderType, int page)
			throws IdeaException;

	IdeaUserListResult listIdeaUser(long ideaId, int page) throws IdeaException;

}
