package com.juzhai.android.idea.service;

import java.util.Map;

import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.idea.exception.IdeaException;

public interface IIdeaService {
	IdeaListResult list(Map<String, String> values, int page)
			throws IdeaException;

	IdeaUserListResult listIdeaUser(Map<String, String> values, int page)
			throws IdeaException;

}
