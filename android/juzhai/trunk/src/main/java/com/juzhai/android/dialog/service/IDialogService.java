package com.juzhai.android.dialog.service;

import android.content.Context;

import com.juzhai.android.core.model.Result.DialogListResult;
import com.juzhai.android.dialog.exception.DialogException;

public interface IDialogService {
	/**
	 * 获取消息对话列表
	 * 
	 * @param page
	 * @return
	 * @throws DialogException
	 */
	DialogListResult list(Context context, int page) throws DialogException;
}
