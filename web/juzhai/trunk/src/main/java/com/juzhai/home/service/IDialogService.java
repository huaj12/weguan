package com.juzhai.home.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.model.DialogContent;

public interface IDialogService {

	/**
	 * 发送私信
	 * 
	 * @param uid
	 * @param targetUid
	 * @param content
	 * @throws DialogException
	 */
	long sendSMS(UserContext context, long targetUid, String content)
			throws DialogException;

	/**
	 * 发送私信(带图片的)
	 * 
	 * @param uid
	 * @param targetUid
	 * @param content
	 * @throws DialogException
	 */
	long sendSMS(UserContext context, long targetUid, String content,
			MultipartFile image) throws DialogException, UploadImageException;

	/**
	 * 根据模版发送私信
	 * 
	 * @param uid
	 * @param targetUid
	 * @param template
	 * @return
	 * @throws DialogException
	 */
	long sendSMS(long uid, long targetUid, DialogContentTemplate template,
			Object... params) throws DialogException;

	/**
	 * 官方发送私信
	 * 
	 * @param targetUid
	 * @param template
	 * @param params
	 * @return
	 */
	long sendOfficialSMS(long targetUid, DialogContentTemplate template,
			Object... params);

	/**
	 * 删除对话
	 * 
	 * @param uid
	 * @param dialogId
	 */
	boolean deleteDialog(long uid, long dialogId);

	/**
	 * 删除回复内容
	 * 
	 * @param uid
	 * @param targetUid
	 * @param dialogContentId
	 */
	long deleteDialogContent(long uid, long targetUid, long dialogContentId);

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
	 * 获取对话内容
	 * 
	 * @param dialogContentId
	 * @return
	 */
	DialogContent getDialogContent(long dialogContentId);

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

}
