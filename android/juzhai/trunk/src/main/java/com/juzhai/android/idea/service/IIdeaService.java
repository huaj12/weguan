package com.juzhai.android.idea.service;

import com.juzhai.android.idea.exception.IdeaException;
import com.juzhai.android.idea.model.IdeaResult;
import com.juzhai.android.idea.model.IdeaUserResult;

public interface IIdeaService {
	IdeaResult list(int categoryId, String orderType, int page)
			throws IdeaException;

	IdeaUserResult listIdeaUser(long ideaId, int page) throws IdeaException;

}
