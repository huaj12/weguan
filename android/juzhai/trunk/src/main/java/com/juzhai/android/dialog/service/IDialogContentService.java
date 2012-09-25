package com.juzhai.android.dialog.service;

import com.juzhai.android.core.model.Result.DialogContentListResult;
import com.juzhai.android.dialog.exception.DialogContentException;

public interface IDialogContentService {
	/**
	 * 获取与某个人的对话
	 * 
	 * @param uid
	 * @param page
	 * @return
	 * @throws DialogContentException
	 */
	DialogContentListResult list(long uid, int page)
			throws DialogContentException;
}
