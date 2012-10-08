package com.juzhai.android.dialog.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.juzhai.android.core.model.PageList;
import com.juzhai.android.dialog.exception.DialogContentException;
import com.juzhai.android.dialog.model.DialogContent;

public interface IDialogContentService {
	/**
	 * 获取与某个人的对话
	 * 
	 * @param uid
	 * @param page
	 * @return
	 * @throws DialogContentException
	 */
	PageList<DialogContent> list(Context context, long uid, int page)
			throws DialogContentException;

	/**
	 * 刷新新内容
	 * 
	 * @param uid
	 * @return
	 */
	PageList<DialogContent> refreshList(Context context, long uid);

	/**
	 * 发送私信
	 * 
	 * @param context
	 * @param uid
	 * @param content
	 * @return
	 */
	DialogContent sendSms(Context context, long uid, String content,
			Bitmap image) throws DialogContentException;

}
