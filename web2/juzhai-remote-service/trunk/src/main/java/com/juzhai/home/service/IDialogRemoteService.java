package com.juzhai.home.service;

import java.util.List;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.model.DialogContent;

public interface IDialogRemoteService {

	/**
	 * 对话列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<DialogView> listDialog(long uid, int firstResult, int maxResults);

	/**
	 * 对话数量
	 * 
	 * @param uid
	 * @return
	 */
	int countDialong(long uid);

	/**
	 * 删除对话
	 * 
	 * @param uid
	 * @param dialogId
	 */
	boolean deleteDialog(long uid, long dialogId);

	/**
	 * 约他
	 * 
	 * @param uid
	 * @param targetUid
	 * @param template
	 * @return
	 * @throws DialogException
	 */
	long sendDatingSMS(UserContext context, long targetUid,
			DialogContentTemplate template, Object... params)
			throws DialogException;

	/**
	 * 对话内容列表
	 * 
	 * @param uid
	 * @param targetId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<DialogContentView> listDialogContent(long uid, long targetUid,
			int firstResult, int maxResults);

	/**
	 * 对话内容数量
	 * 
	 * @param uid
	 * @param targetUid
	 * @return
	 */
	int countDialogContent(long uid, long targetUid);

	/**
	 * 发送私信(带图片的)
	 * 
	 * @param uid
	 * @param targetUid
	 * @param content
	 * @throws DialogException
	 */
	long sendSMS(UserContext context, long targetUid, String content,
			String imagePath) throws DialogException, UploadImageException;

	/**
	 * 获取对话内容
	 * 
	 * @param dialogContentId
	 * @return
	 */
	DialogContent getDialogContent(long dialogContentId);
}
