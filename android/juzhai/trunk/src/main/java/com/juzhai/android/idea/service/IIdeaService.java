package com.juzhai.android.idea.service;

import android.content.Context;

import com.juzhai.android.core.model.Result.IdeaListResult;
import com.juzhai.android.core.model.Result.IdeaResult;
import com.juzhai.android.core.model.Result.IdeaUserListResult;
import com.juzhai.android.idea.exception.IdeaException;

public interface IIdeaService {
	/**
	 * 好主意列表
	 * 
	 * @param context
	 * @param categoryId
	 * @param orderType
	 * @param page
	 * @return
	 * @throws IdeaException
	 */
	IdeaListResult list(Context context, long categoryId, String orderType,
			int page) throws IdeaException;

	/**
	 * 好主意想去的人列表
	 * 
	 * @param context
	 * @param ideaId
	 * @param city
	 * @param gender
	 * @param page
	 * @return
	 * @throws IdeaException
	 */
	IdeaUserListResult listIdeaUser(Context context, long ideaId, long city,
			Integer gender, int page) throws IdeaException;

	/**
	 * 显示好主意
	 * 
	 * @param context
	 * @param ideaId
	 * @return
	 * @throws IdeaException
	 */
	IdeaResult showIdea(Context context, long ideaId) throws IdeaException;

}
